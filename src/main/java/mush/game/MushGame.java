package mush.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mush.command.ActionCommandType;
import mush.game.action.Action;
import mush.game.action.MushKillAction;
import mush.game.ai.AI;
import mush.game.ai.ChannelHandler;
import mush.game.ai.Narrator;
import mush.game.ai.VoteCounter;
import mush.game.player.Player;
import mush.game.player.role.RoleValues;
import mush.properties.GameProperties;
import util.message.MessagePack;
import chat.Channel;
import chat.MushBot;
import chat.User;

public class MushGame{

	public static final String MUSH_CHANNEL_PREFIX = "mush_channel_";

	private static List<MushGameStatus> phases;

	private Thread timeoutThread;

	private MushBot bot;
	private GameProperties gameProperties;

	private Channel mainChannel;
	private Narrator narrator;

	private Map<User, Player> usersMap;
	private MushGameStatus status;
	private Tripulation tripulation;
	private AI ai;
	private Validator validator;

	private VoteCounter voteCounter;

	private List<Action> roundActions;
	private List<Player> standByDeaths;

	static {
		initPhases();
	}

	public MushGame(MushBot bot, GameProperties gameProperties,
			Channel mainChannel) {
		this.bot = bot;
		this.gameProperties = gameProperties;
		this.mainChannel = mainChannel;
		this.narrator = new Narrator(bot, mainChannel);

		usersMap = new HashMap<User, Player>();
		tripulation = new Tripulation();
		timeoutThread = new Thread(new TimeoutThread(this));
		ai = new AI(gameProperties);
		validator = new Validator(this);
		newGame();
	}

	public static void initPhases() {
		phases = new ArrayList<MushGameStatus>();
		phases.add(MushGameStatus.JOINING_PHASE);
		phases.add(MushGameStatus.STARTING_PHASE);
		phases.add(MushGameStatus.MUSH_ATTACK_PHASE);
		phases.add(MushGameStatus.ACTIONS_PHASE);
		phases.add(MushGameStatus.VOTING_PHASE);
		phases.add(MushGameStatus.ENDED);
	}

	/*** Game functions ***/

	private void newGame() {
		startPhase();
		timeoutThread.start();
	}

	public void startGame() {
		if (ai.canStart(tripulation)) {
			nextPhase();
			ai.startGame(tripulation);
			narrator.announceTripulation(tripulation);
			nextPhase();
		} else {
			narrator.announceRequiredPlayers(ai.getRequiredPlayers(),
					gameProperties.getMinMushAmount());
		}
	}

	public void endGame() {
		status = MushGameStatus.ENDED;
		bot.endGame(mainChannel);
	}

	public void addPlayer(User user) {
		Player player = new Player(user);
		tripulation.addPlayer(player);
		usersMap.put(user, player);
	}

	public boolean isPlaying(User user) {
		return usersMap.containsKey(user);
	}

	/*** Phases booleans ***/

	public boolean hasStarted() {
		return status.hasStarted();
	}

	public boolean isInJoiningPhase() {
		return status.isJoiningPhase();
	}

	public boolean isInMushAttackPhase() {
		return status.isMushAttackPhase();
	}

	public boolean isInVotingPhase() {
		return status.isInVotingPhase();
	}

	/*** Mush Attack ***/

	void startMushAttack() {
		bot.silenceChannel(mainChannel);
		narrator.announceMushAttack(tripulation);
		joinMushChannel();
	}

	void endMushAttack() {
		if (!voteCounter.isVotationEven()) {
			concludeVoting();
		} else {
			endGame();
		}
	}

	/*** Mush channel ***/

	private void joinMushChannel() {
		bot.joinChannel(getMushChannelName());
	}

	private String getMushChannelName() {
		return "#" + MUSH_CHANNEL_PREFIX
				+ String.valueOf(System.currentTimeMillis());
	}

	public void setMushChannel(Channel mushChannel) {
		narrator.setMushChannel(mushChannel);
		ChannelHandler.prepareMushChannel(mushChannel);
		inviteUsersToChannel(tripulation.getMushUsers(), mushChannel);
	}

	private void inviteUsersToChannel(List<User> users, Channel channel) {
		for (User user : users) {
			bot.inviteToChannel(user, channel);
		}
	}

	/*** Phase control ***/

	private void startPhase() {
		status = phases.get(0);
	}

	private void nextPhase() {
		int i = phases.indexOf(status) + 1;
		if (i < phases.size()) {
			status = phases.get(i);
		}
		if (isInMushAttackPhase()) {
			startMushAttack();
		}
		if (isFirstCyclePhase()) {
			roundActions = new ArrayList<Action>();
			standByDeaths = new ArrayList<Player>();
		}
		if (status.isVotingPhase()) {
			initVoting();
		}
		if (status.isActionsPhase()) {
			executeActions();
		}
		if (status.isEnded()) {
			endGame();
		}
	}

	private boolean isFirstCyclePhase() {
		return status.equals(phases.get(2));
	}

	/*** Voting ***/

	private void initVoting() {
		switch (status) {
		case MUSH_ATTACK_PHASE:
			voteCounter = new VoteCounter(tripulation.getMush(),
					tripulation.getHumans());
			break;
		default:
			break;
		}
	}

	private void concludeVoting() {
		User electedUser = voteCounter.getElected();
		switch (status) {
		case MUSH_ATTACK_PHASE:
			roundActions.add(new MushKillAction(tripulation.getRandomMush(),
					getPlayer(electedUser)));
			narrator.announceMushVoteResult(electedUser);
			break;
		default:
			break;
		}
		nextPhase();
	}

	private void vote(User user, String string) {
		boolean hasVoted = hasVoted(user);
		if (hasVoted) {
			voteCounter.removeVote(user);
		}
		User voted = voteCounter.vote(user, string);
		if (isInMushAttackPhase()) {
			narrator.announceMushVote(user, voted);
		} else {
			narrator.announceVote(user, voted);
		}
		if (isAnElectedUser()) {
			concludeVoting();
		}
	}

	private void check(User user, String nick) {

		User toCheckUser = this.getUser(nick);
		
		if (getPlayer(toCheckUser).isMush()) {
			narrator.announceUserCheck(user, nick, RoleValues.ROLE_MUSH);
		} else{
			narrator.announceUserCheck(user, nick, RoleValues.ROLE_HUMAN);
		}
	}
	
	boolean canVote(User user) {
		return !hasVoted(user) || gameProperties.isRevotingAllowed();
	}

	boolean isVotable(String prefix) {
		return voteCounter.isVotable(prefix);
	}

	User getVoted(String string) {
		return voteCounter.getVoted(string);
	}

	private boolean hasVoted(User user) {
		return voteCounter.hasVoted(user);
	}

	private boolean isAnElectedUser() {
		return voteCounter.isConcluded()
				|| (voteCounter.everyoneHasVoted()
						&& voteCounter.hasLeaderVoter() && voteCounter
							.leaderHasVotedMostVoted());
	}

	/*** Actions ***/

	public void killPlayer(Player player) {
		standByDeaths.add(player);
	}

	private void executeActions() {
		for (Action action : roundActions) {
			action.execute(this);
			MessagePack pack = action.getVisibleMessagePack();
			narrator.announceAction(pack.getKey(), pack.getArgs());
		}
		commitDeaths();
		nextPhase();
	}

	private void commitDeaths() {
		for (Player player : standByDeaths) {
			tripulation.killPlayer(player);
			narrator.announceDeath(player);
		}
	}

	/*** Command Actions ***/

	public boolean canPerformAction(User user,
			ActionCommandType actionCommandType) {
		return validator.canPerformAction(user, actionCommandType);
	}

	public boolean isCorrectTime(ActionCommandType actionCommandType) {
		return validator.isCorrectTime(actionCommandType);
	}

	public MessagePack getActionErrors(User user,
			ActionCommandType actionCommandType, List<String> args) {
		return validator.getActionErrors(user, actionCommandType, args);
	}

	public void performAction(User user, ActionCommandType actionCommandType,
			List<String> args) {
		switch (actionCommandType) {
		case MUSH_VOTE:
		case VOTE:
			vote(user, args.get(1));
			break;
		case MUSH_CHECK:
			check(user, args.get(1));
			break;
		default:
			break;
		}
	}

	/*** Getters ***/

	MushGameStatus getStatus() {
		return status;
	}

	GameProperties getGameProperties() {
		return gameProperties;
	}

	boolean isMush(User user) {
		return usersMap.get(user).isMush();
	}

	boolean is(User user, String role) {
		return usersMap.get(user).is(role);
	}
	
	private Player getPlayer(User user) {
		return usersMap.get(user);
	}
	
	public User getUser(String nick){
		for(User user : usersMap.keySet()) {
		    if(user.getNick().equals(nick)){
		    	return user;
		    }
		}
		return null;
	}
}

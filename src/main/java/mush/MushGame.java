package mush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mush.action.Action;
import mush.action.MushKillAction;
import mush.ai.AI;
import mush.ai.ChannelHandler;
import mush.ai.Narrator;
import mush.ai.VoteCounter;
import mush.player.Player;
import mush.properties.GameProperties;

import org.pircbotx.Channel;
import org.pircbotx.User;

import util.MessagePack;
import bot.MushBot;

public class MushGame implements MushValues, Runnable {

	public static final String MUSH_CHANNEL_PREFIX = "mush_channel_";

	private static List<MushGameStatus> phases;

	private Thread timeoutsThread;

	private MushBot bot;
	private GameProperties gameProperties;

	private Narrator narrator;
	private Channel mushChannel;
	private MushGameStatus status;
	private Map<User, Player> usersMap;
	private Tripulation tripulation;
	private AI ai;

	private VoteCounter voteCounter;

	private List<Action> roundActions;
	private List<Player> standByDeaths;

	static {
		phases = new ArrayList<MushGameStatus>();
		phases.add(MushGameStatus.JOINING_PHASE);
		phases.add(MushGameStatus.STARTING_PHASE);
		phases.add(MushGameStatus.MUSH_ATTACK_PHASE);
		phases.add(MushGameStatus.ACTIONS_PHASE);
		phases.add(MushGameStatus.ENDED);
	}

	public MushGame(MushBot bot, GameProperties gameProperties) {
		this.bot = bot;
		this.gameProperties = gameProperties;
		this.narrator = new Narrator(bot);
		usersMap = new HashMap<User, Player>();
		tripulation = new Tripulation();
		timeoutsThread = new Thread(this);
		ai = new AI(gameProperties);
		newGame();
	}

	public void newGame() {
		startPhase();
		timeoutsThread.start();
	}

	public void addPlayer(User user) {
		Player player = new Player(user);
		tripulation.addPlayer(player);
		usersMap.put(user, player);
	}

	public void startGame() {
		if (ai.canStart(tripulation.getPlayers())) {
			nextPhase();
			ai.startGame(tripulation.getPlayers());
			tripulation.build();
			narrator.announceTripulation(tripulation);
			startMushAttack();
		} else {
			narrator.announceRequiredPlayers(ai.getRequiredPlayers(),
					gameProperties.getMinMushAmount());
		}
	}

	public void endMushAttack() {
		if (!voteCounter.isVotationEven()) {
			concludeVoting();
		} else {
			endGame();
		}
	}

	public void vote(User user, String string) {
		boolean hasVoted = hasVoted(user);
		if (hasVoted) {
			voteCounter.removeVote(user);
		}
		voteCounter.vote(user, string);
		if (isAnElectedUser()) {
			concludeVoting();
		}
	}

	public boolean canVote(User user) {
		return !hasVoted(user) && !gameProperties.isRevotingAllowed();
	}

	public boolean isVotable(String prefix) {
		return voteCounter.isVotable(prefix);
	}

	public User getVoted(String string) {
		return voteCounter.getVoted(string);
	}

	public void killPlayer(Player player) {
		standByDeaths.add(player);
	}

	public void endGame() {
		status = MushGameStatus.ENDED;
		bot.endGame();
	}

	public void run() {
		while (!status.isEnded()) {
			long initTime, currTime;
			switch (status) {
			case JOINING_PHASE:
				initTime = System.currentTimeMillis();
				do {
					currTime = System.currentTimeMillis();
				} while (currTime - initTime < gameProperties
						.getJoiningPhaseTimeout() * 1000
						&& status.isJoiningPhase());
				if (status.isJoiningPhase()) {
					startGame();
				}
				break;
			case MUSH_ATTACK_PHASE:
				initTime = System.currentTimeMillis();
				do {
					currTime = System.currentTimeMillis();
				} while (currTime - initTime < gameProperties
						.getMushAttackPhaseTimeout() * 1000
						&& status.isMushAttackPhase());
				if (status.isMushAttackPhase()) {
					endMushAttack();
				}
				break;
			default:
				break;

			}
		}
	}

	public boolean hasStarted() {
		return status.hasStarted();
	}

	public boolean isInJoiningPhase() {
		return status.isJoiningPhase();
	}

	public boolean isInMushAttackPhase() {
		return status.isMushAttackPhase();
	}

	public boolean isPlaying(User user) {
		return usersMap.containsKey(user);
	}

	public boolean isMush(User user) {
		return usersMap.get(user).isMush();
	}

	public Set<User> getUsers() {
		return new HashSet<User>(usersMap.keySet());
	}

	public Narrator getNarrator() {
		return narrator;
	}

	public Player getPlayer(User user) {
		return usersMap.get(user);
	}

	private void startMushAttack() {
		bot.silenceChannel();
		nextPhase();
		narrator.announceMushAttack(tripulation);
		joinMushChannel();
		bot.inviteToChannel(mushChannel, tripulation.getMushUsers());
	}

	private void joinMushChannel() {
		bot.joinChannel(getMushChannelName());
	}

	private String getMushChannelName() {
		return "#" + MUSH_CHANNEL_PREFIX
				+ String.valueOf(System.currentTimeMillis());
	}

	private void startPhase() {
		status = phases.get(0);
	}

	private void nextPhase() {
		int i = phases.indexOf(status) + 1;
		if (i < phases.size()) {
			status = phases.get(i);
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

	private boolean hasVoted(User user) {
		return voteCounter.hasVoted(user);
	}

	private boolean isAnElectedUser() {
		return voteCounter.isConcluded()
				|| (voteCounter.everyoneHasVoted()
						&& voteCounter.hasLeaderVoter() && voteCounter
							.leaderHasVotedMostVoted());
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

	public void setMushChannel(Channel mushChannel) {
		this.mushChannel = mushChannel;
		narrator.setMushChannel(mushChannel);
		ChannelHandler.prepareMushChannel(mushChannel);
	}
}

package mush.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import mush.game.action.GameAction;
import mush.game.error.InvalidCreateError;
import mush.game.event.MushGameEventListener;
import mush.game.event.MushGameEventMulticaster;
import mush.game.player.Player;
import mush.game.properties.GameProperties;
import util.PrefixManager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// TODO : Handle nick changes <==> PrefixManager
/**
 * Class that represents a Mush Game.
 * 
 * @author Tomas
 */
public class Game {

	private Status status;
	private BiMap<String, Player> players;
	private TimeoutTask timeoutTask;
	private Thread timeoutThread;
	@SuppressWarnings("unused")
	private TreeSet<GameAction> pendingActions;
	private GameProperties gameProperties;
	private PrefixManager prefixManager;
	private MushGameEventMulticaster eventMulticaster;

	public Game() {
		this.eventMulticaster = new MushGameEventMulticaster();
		init();
	}

	private void init() {
		this.status = new Status();
		this.players = HashBiMap.create();
		this.prefixManager = new PrefixManager();
		this.pendingActions = new TreeSet<GameAction>();
		this.timeoutTask = new TimeoutTask(this);
		if (timeoutThread != null) {
			timeoutThread.interrupt();
		}
		this.timeoutThread = new Thread(timeoutTask);
		timeoutThread.start();
	}

	/**
	 * Tries to create a new game over this game. If a new game cannot be
	 * created, then an {@link InvalidCreateError} is thrown with the
	 * eventMulticaster.
	 * 
	 * @param user
	 *            User that is trying to create a new game.
	 * @param gameProperties
	 *            {@link GameProperties} that are going to be used for the new
	 *            game.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null.
	 */
	public void createGame(GameUser user, GameProperties gameProperties) {
		if (user == null || gameProperties == null) {
			throw new IllegalArgumentException();
		}
		if (!status.canCreateGame()) {
			eventMulticaster.onError(new InvalidCreateError(user));
			return;
		}
		init();
		this.gameProperties = gameProperties;
		eventMulticaster.onCreateGame();
		if (!status.hasEnded()) {
			status.next();
		}
	}

	/**
	 * Actionates the action received.
	 * 
	 * @param action
	 * @throws IllegalArgumentException
	 *             If {@code action} is null.
	 */
	public void performAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		action.action(this);
	}

	/**
	 * Returns {@code true} if it has ended.
	 * 
	 * @return {@code true} if it has ended.
	 */
	public boolean hasEnded() {
		return status.hasEnded();
	}

	/**
	 * Subscribes a {@link MushGameEventListener} to the Mush Game event
	 * multicaster.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If {@code listener} is null.
	 */
	public void addMushGameEventListener(MushGameEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		eventMulticaster.addMushGameEventListener(listener);
	}

	/**
	 * Returns the amount of required players to start a Mush Game.
	 * 
	 * @return the amount of required players to start a Mush Game.
	 */
	public int getRequiredPlayers() {
		return (int) Math.round(gameProperties.getMinMushAmount()
				/ gameProperties.getMushProportion());
	}

	public GameProperties getGameProperties() {
		return gameProperties;
	}

	public Status getStatus() {
		return status;
	}

	public Map<String, Player> getPlayersMap() {
		return players;
	}

	public List<Player> getPlayers() {
		return new ArrayList<Player>(players.values());
	}

	public MushGameEventMulticaster getEventMulticaster() {
		return eventMulticaster;
	}

	public PrefixManager getPrefixManager() {
		return prefixManager;
	}

	public TimeoutTask getTimeoutTask() {
		return timeoutTask;
	}

	public Thread getTimeoutThread() {
		return timeoutThread;
	}

	//
	// private static List<MushGameStatus> phases;
	//
	// private Thread timeoutThread;
	//
	// private MushBot bot;
	// private GameProperties gameProperties;
	//
	// private ChatChannel mainChannel;
	// private Narrator narrator;
	//
	// private Map<ChatUser, Player> usersMap;
	// private MushGameStatus status;
	// private Tripulation tripulation;
	// private AI ai;
	// private Validator validator;
	//
	// private VoteCounter voteCounter;
	//
	// private List<Action> roundActions;
	// private List<Player> standByDeaths;
	//
	// static {
	// initPhases();
	// }
	//
	// public MushGame(MushBot bot, GameProperties gameProperties,
	// ChatChannel mainChannel) {
	// this.bot = bot;
	// this.gameProperties = gameProperties;
	// this.mainChannel = mainChannel;
	// this.narrator = new Narrator(bot, mainChannel);
	//
	// usersMap = new HashMap<ChatUser, Player>();
	// tripulation = new Tripulation();
	// timeoutThread = new Thread(new TimeoutThread(this));
	// ai = new AI(gameProperties);
	// validator = new Validator(this);
	// newGame();
	// }
	//
	// public static void initPhases() {
	// phases = new ArrayList<MushGameStatus>();
	// phases.add(MushGameStatus.JOINING_PHASE);
	// phases.add(MushGameStatus.STARTING_PHASE);
	// phases.add(MushGameStatus.MUSH_ATTACK_PHASE);
	// phases.add(MushGameStatus.ACTIONS_PHASE);
	// phases.add(MushGameStatus.VOTING_PHASE);
	// phases.add(MushGameStatus.ENDED);
	// }
	//
	// /*** Game functions ***/
	//
	// private void newGame() {
	// startPhase();
	// timeoutThread.start();
	// }
	//
	// public void startGame() {
	// if (ai.canStart(tripulation)) {
	// nextPhase();
	// ai.startGame(tripulation);
	// narrator.announceTripulation(tripulation);
	// nextPhase();
	// } else {
	// narrator.announceRequiredPlayers(ai.getRequiredPlayers(),
	// gameProperties.getMinMushAmount());
	// }
	// }
	//
	// public void endGame() {
	// status = MushGameStatus.ENDED;
	// bot.endGame(mainChannel);
	// }
	//
	// public void addPlayer(ChatUser user) {
	// Player player = new Player(user);
	// tripulation.addPlayer(player);
	// usersMap.put(user, player);
	// }
	//
	// public boolean isPlaying(ChatUser user) {
	// return usersMap.containsKey(user);
	// }
	//
	// /*** Phases booleans ***/
	//
	// public boolean hasStarted() {
	// return status.hasStarted();
	// }
	//
	// public boolean isInJoiningPhase() {
	// return status.isJoiningPhase();
	// }
	//
	// public boolean isInMushAttackPhase() {
	// return status.isMushAttackPhase();
	// }
	//
	// public boolean isInVotingPhase() {
	// return status.isInVotingPhase();
	// }
	//
	// /*** Mush Attack ***/
	//
	// void startMushAttack() {
	// bot.silenceChannel(mainChannel);
	// narrator.announceMushAttack(tripulation);
	// joinMushChannel();
	// }
	//
	// void endMushAttack() {
	// if (!voteCounter.isVotationEven()) {
	// concludeVoting();
	// } else {
	// endGame();
	// }
	// }
	//
	// /*** Mush channel ***/
	//
	// private void joinMushChannel() {
	// bot.joinChannel(getMushChannelName());
	// }
	//
	// private String getMushChannelName() {
	// return "#" + MUSH_CHANNEL_PREFIX
	// + String.valueOf(System.currentTimeMillis());
	// }
	//
	// public void setMushChannel(ChatChannel mushChannel) {
	// narrator.setMushChannel(mushChannel);
	// ChannelHandler.prepareMushChannel(mushChannel);
	// inviteUsersToChannel(tripulation.getMushUsers(), mushChannel);
	// }
	//
	// private void inviteUsersToChannel(List<ChatUser> users, ChatChannel
	// channel) {
	// for (ChatUser user : users) {
	// bot.inviteToChannel(user, channel);
	// }
	// }
	//
	// /*** Phase control ***/
	//
	// private void startPhase() {
	// status = phases.get(0);
	// }
	//
	// private void nextPhase() {
	// int i = phases.indexOf(status) + 1;
	// if (i < phases.size()) {
	// status = phases.get(i);
	// }
	// if (isInMushAttackPhase()) {
	// startMushAttack();
	// }
	// if (isFirstCyclePhase()) {
	// roundActions = new ArrayList<Action>();
	// standByDeaths = new ArrayList<Player>();
	// }
	// if (status.isVotingPhase()) {
	// initVoting();
	// }
	// if (status.isActionsPhase()) {
	// executeActions();
	// }
	// if (status.isEnded()) {
	// endGame();
	// }
	// }
	//
	// private boolean isFirstCyclePhase() {
	// return status.equals(phases.get(2));
	// }
	//
	// /*** Voting ***/
	//
	// private void initVoting() {
	// switch (status) {
	// case MUSH_ATTACK_PHASE:
	// voteCounter = new VoteCounter(tripulation.getMush(),
	// tripulation.getHumans());
	// break;
	// default:
	// break;
	// }
	// }
	//
	// private void concludeVoting() {
	// ChatUser electedUser = voteCounter.getElected();
	// switch (status) {
	// case MUSH_ATTACK_PHASE:
	// roundActions.add(new MushKillAction(tripulation.getRandomMush(),
	// getPlayer(electedUser)));
	// narrator.announceMushVoteResult(electedUser);
	// break;
	// default:
	// break;
	// }
	// nextPhase();
	// }
	//
	// private void vote(ChatUser user, String string) {
	// boolean hasVoted = hasVoted(user);
	// if (hasVoted) {
	// voteCounter.removeVote(user);
	// }
	// ChatUser voted = voteCounter.vote(user, string);
	// if (isInMushAttackPhase()) {
	// narrator.announceMushVote(user, voted);
	// } else {
	// narrator.announceVote(user, voted);
	// }
	// if (isAnElectedUser()) {
	// concludeVoting();
	// }
	// }
	//
	// boolean canVote(ChatUser user) {
	// return !hasVoted(user) || gameProperties.isRevotingAllowed();
	// }
	//
	// boolean isVotable(String prefix) {
	// return voteCounter.isVotable(prefix);
	// }
	//
	// ChatUser getVoted(String string) {
	// return voteCounter.getVoted(string);
	// }
	//
	// private boolean hasVoted(ChatUser user) {
	// return voteCounter.hasVoted(user);
	// }
	//
	// private boolean isAnElectedUser() {
	// return voteCounter.isConcluded()
	// || (voteCounter.everyoneHasVoted()
	// && voteCounter.hasLeaderVoter() && voteCounter
	// .leaderHasVotedMostVoted());
	// }
	//
	// /*** Actions ***/
	//
	// public void killPlayer(Player player) {
	// standByDeaths.add(player);
	// }
	//
	// private void executeActions() {
	// for (Action action : roundActions) {
	// action.execute(this);
	// Message pack = action.getVisibleMessagePack();
	// narrator.announceAction(pack.getKey(), pack.getArgs());
	// }
	// commitDeaths();
	// nextPhase();
	// }
	//
	// private void commitDeaths() {
	// for (Player player : standByDeaths) {
	// tripulation.killPlayer(player);
	// narrator.announceDeath(player);
	// }
	// }
	//
	// /*** Command Actions ***/
	//
	// public boolean canPerformAction(ChatUser user,
	// ActionCommandType actionCommandType) {
	// return validator.canPerformAction(user, actionCommandType);
	// }
	//
	// public boolean isCorrectTime(ActionCommandType actionCommandType) {
	// return validator.isCorrectTime(actionCommandType);
	// }
	//
	// public Message getActionErrors(ChatUser user,
	// ActionCommandType actionCommandType, List<String> args) {
	// return validator.getActionErrors(user, actionCommandType, args);
	// }
	//
	// public void performAction(ChatUser user, ActionCommandType
	// actionCommandType,
	// List<String> args) {
	// switch (actionCommandType) {
	// case MUSH_VOTE:
	// case VOTE:
	// vote(user, args.get(1));
	// default:
	// break;
	// }
	// }
	//
	// /*** Getters ***/
	//
	// MushGameStatus getStatus() {
	// return status;
	// }
	//
	// GameProperties getGameProperties() {
	// return gameProperties;
	// }
	//
	// boolean isMush(ChatUser user) {
	// return usersMap.get(user).isMush();
	// }
	//
	// private Player getPlayer(ChatUser user) {
	// return usersMap.get(user);
	// }
}

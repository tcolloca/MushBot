package mush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mush.action.Action;
import mush.ai.AI;
import mush.ai.Narrator;
import mush.ai.VoteCounter;
import mush.player.Player;

import org.pircbotx.User;

import util.Timeouts;
import bot.MushBot;

public class MushGame implements MushValues, Runnable {

	private static List<MushGameStatus> phases;

	private Thread timeoutsThread;

	private MushBot bot;
	private Narrator narrator;
	private MushGameStatus status;
	private Map<User, Player> usersMap;
	private Timeouts timeouts;
	private Tripulation tripulation;
	private AI ai;
	private VoteCounter voteCounter;
	private List<Action> roundActions;

	static {
		phases = new ArrayList<MushGameStatus>();
		phases.add(MushGameStatus.JOINING_PHASE);
		phases.add(MushGameStatus.STARTING_PHASE);
		phases.add(MushGameStatus.MUSH_ATTACK_PHASE);
		phases.add(MushGameStatus.ENDED);
	}

	public MushGame(MushBot bot) {
		this.bot = bot;
		narrator = new Narrator(bot);
		usersMap = new HashMap<User, Player>();
		tripulation = new Tripulation();
		timeouts = new Timeouts();
		timeoutsThread = new Thread(this);
		ai = new AI(bot);
		newGame();
	}

	public void newGame() {
		startPhase();
		narrator.announceNewGameCreated();
		timeoutsThread.start();
	}

	public void addPlayer(User user) {
		Player player = new Player(user);
		tripulation.addPlayer(player);
		usersMap.put(user, player);
		narrator.announcePlayerJoins(user);
	}

	public void startGame() {
		if (ai.canStart(tripulation.getPlayers())) {
			nextPhase();
			narrator.announceGameStarts();
			ai.startGame(tripulation.getPlayers());
			tripulation.build();
			narrator.announceTripulation(tripulation);
			startMushAttack();
		} else {
			narrator.announceRequiredPlayers(ai.getRequiredPlayers(),
					ai.getMinMushAmount());
		}
	}

	private void startMushAttack() {
		nextPhase();
		narrator.announceMushAttack(tripulation);
	}

	public void endMushAttack() {
		concludeVoting();
	}

	public void vote(User user, String string) {
		User voted;
		boolean hasVoted = voteCounter.hasVoted(user);
		if (hasVoted && !ai.isAllowedRevoting()) {
			narrator.announceAlreadyVoted(user);
		} else {
			if (hasVoted) {
				voteCounter.removeVote(user);
			}
			if ((voted = voteCounter.vote(user, string)) == null) {
				narrator.announceUknownVote(user, string);
			} else {
				narrator.announceVote(user, voted);
			}
		}
		if (voteCounter.isConcluded()) {
			concludeVoting();
		}
	}

	public void run() {
		while (!status.isEnded()) {
			long initTime, currTime;
			switch (status) {
			case JOINING_PHASE:
				initTime = System.currentTimeMillis();
				do {
					currTime = System.currentTimeMillis();
				} while (currTime - initTime < timeouts
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
				} while (currTime - initTime < timeouts
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
		}
		if (status.isVotingPhase()) {
			initVoting();
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
		List<User> mostVotedUsers = voteCounter.mostVoted();
		User mostVotedUser;
		mostVotedUser = mostVotedUsers.get(0); // TODO
		switch (status) {
		case MUSH_ATTACK_PHASE:
			// TODO: Add action
			narrator.announceVoteResult(mostVotedUser);
			break;
		default:
			break;
		}
		nextPhase();
	}
}

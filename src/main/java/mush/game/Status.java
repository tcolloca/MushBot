package mush.game;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the current phase of the Game.
 * 
 * @author Tomas
 */
public class Status {

	private static List<Phase> phases;

	private Phase currentPhase;

	static {
		initPhases();
	}

	public static void initPhases() {
		phases = new ArrayList<Phase>();
		phases.add(Phase.CREATE);
		phases.add(Phase.JOIN);
		phases.add(Phase.PRE_MUSH_ATTACK);
		phases.add(Phase.MUSH_ATTACK);
		phases.add(Phase.VOTE);
		phases.add(Phase.END);
	}

	public Status() {
		currentPhase = phases.get(0);
	}

	/**
	 * Returns {@code true} if the current phase allows to create a new game.
	 * 
	 * @return {@code true} if the current phase allows to create a new game.
	 */
	public boolean canCreateGame() {
		return currentPhase.isCreatePhase() || currentPhase.isEndPhase();
	}

	/**
	 * Returns {@code true} if the current phase allows to start the game.
	 * 
	 * @return {@code true} if the current phase allows to start the game.
	 */
	public boolean canStartGame() {
		return currentPhase.isJoinPhase();
	}

	/**
	 * Returns {@code true} if the current phase allows a player to join the
	 * game.
	 * 
	 * @return {@code true} if the current phase allows a player to join the
	 *         game.
	 */
	public boolean canJoinToGame() {
		return currentPhase.isJoinPhase();
	}

	/**
	 * Returns {@code true} if the current phase allows to stop the game.
	 * 
	 * @return {@code true} if the current phase allows to stop the game.
	 */
	public boolean canStopGame() {
		return !hasEnded();
	}

	/**
	 * Returns {@code true} if the current game has ended.
	 * 
	 * @return {@code true} if the current game has ended.
	 */
	public boolean hasEnded() {
		return currentPhase.isEndPhase();
	}

	/**
	 * Changes the current phase to the next one.
	 * 
	 * @throws IllegalStateException
	 *             If there are no more phases.
	 */
	public void next() {
		int index = phases.indexOf(currentPhase) + 1;
		if (index >= phases.size()) {
			throw new IllegalStateException();
		}
		currentPhase = phases.get(index);
	}

	/**
	 * Stops the game.
	 * 
	 * @throws IllegalStateException
	 *             If the game cannot be stopped.
	 */
	public void stop() {
		if (!canStopGame()) {
			throw new IllegalStateException();
		}
		currentPhase = phases.get(phases.size() - 1);
	}

	public Phase getPhase() {
		return currentPhase;
	}
}

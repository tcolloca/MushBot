package mush.game.event;

import mush.game.GameUser;
import mush.game.Phase;
import mush.game.action.GameAction;
import mush.game.error.MushGameError;

public interface MushGameEventListener {

	/**
	 * Action to be performed when a new Mush Game has been created.
	 */
	public void onCreateGame();

	/**
	 * Action to be performed when a the current Mush Game has been started.
	 */
	public void onStartGame();

	/**
	 * Action to be performed when a the current Mush Game has been stopped.
	 */
	public void onStopGame();

	/**
	 * Action to be performed when a player has joined the game.
	 * 
	 * @param user
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public void onAddPlayer(GameUser user);

	/**
	 * Action to be performed when an action was performed.
	 * 
	 * @param action
	 * @throws IllegalArgumentException
	 *             If {@code action} is null.
	 */
	public void onPerformAction(GameAction action);

	/**
	 * Action to be performed when it is missing only {@code missingTime}
	 * seconds to end the given phase.
	 * 
	 * @param currentPhase
	 * @param missingTime
	 * @throws IllegalArgumentException
	 *             If {@code currentPhase} is null.
	 */
	public void onTimeoutNotification(Phase currentPhase, long missingTime);

	/**
	 * Action to be performed when an error occurred.
	 * 
	 * @param error
	 * @throws IllegalArgumentException
	 *             If {@code error} is null.
	 */
	public void onError(MushGameError error);
}

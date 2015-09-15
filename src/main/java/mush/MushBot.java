package mush;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.Phase;
import mush.game.action.GameAction;
import mush.game.action.player.StopAction;
import mush.game.error.MushGameError;
import mush.game.error.NotEnoughPlayersInGameError;
import mush.game.event.MushGameEventListener;
import mush.game.properties.GameProperties;

/**
 * This class performs different actions over a Mush Game.
 * 
 * @author Tomas
 */
public class MushBot implements MushGameEventListener {

	private Game mushGame;
	private GameProperties gameProperties;

	public MushBot() {
		mushGame = new Game();
		gameProperties = new GameProperties();
		mushGame.addMushGameEventListener(this);
	}

	/**
	 * Tries to create a new Mush Game.
	 * 
	 * @param user
	 *            {@link GameUser} that created the game.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public void createGame(GameUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		mushGame.createGame(user, gameProperties);
	}

	/**
	 * Tries to perform an action over the Mush Game.
	 * 
	 * @param action
	 * @throws IllegalArgumentException
	 *             If {@code action} is null.
	 */
	public void performAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		mushGame.performAction(action);
	}

	/**
	 * Returns {@code true} if the Mush Game has ended.
	 * 
	 * @return {@code true} if the Mush Game has ended.
	 */
	public boolean hasEnded() {
		return mushGame.hasEnded();
	}

	/**
	 * Adds a {@link MushGameEventListener} to the Mush Game.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If {@code listener} is null.
	 */
	public void addMushGameEventListener(MushGameEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		mushGame.addMushGameEventListener(listener);
	}

	@Override
	public void onCreateGame() {
	}

	@Override
	public void onStartGame() {
	}

	@Override
	public void onStopGame() {
	}

	@Override
	public void onAddPlayer(GameUser user) {
	}

	@Override
	public void onPerformAction(GameAction action) {
	}

	@Override
	public void onTimeoutNotification(Phase currentPhase, long missingTime) {
	}

	@Override
	public void onError(MushGameError error) {
		if (error == null) {
			throw new IllegalArgumentException();
		}
		if (error instanceof NotEnoughPlayersInGameError) {
			NotEnoughPlayersInGameError e = (NotEnoughPlayersInGameError) error;
			if (e.getTryNumber() >= gameProperties.getStartMaxTryNumber()) {
				mushGame.performAction(new StopAction());
			}
		}
	}
}

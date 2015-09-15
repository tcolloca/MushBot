package mush.game.event;

import java.util.HashSet;
import java.util.Set;

import mush.game.GameUser;
import mush.game.Phase;
import mush.game.action.GameAction;
import mush.game.error.MushGameError;

/**
 * Event broadcaster for all the Mush Game Events.
 * 
 * @author Tomas
 */
public class MushGameEventMulticaster implements MushGameEventListener {

	private Set<MushGameEventListener> listeners;

	public MushGameEventMulticaster() {
		listeners = new HashSet<MushGameEventListener>();
	}

	/**
	 * Subscribes a listener to all the Mush Game Events.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If {@code listener} is null.
	 */
	public void addMushGameEventListener(MushGameEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void onCreateGame() {
		for (MushGameEventListener listener : listeners) {
			listener.onCreateGame();
		}
	}

	@Override
	public void onStartGame() {
		for (MushGameEventListener listener : listeners) {
			listener.onStartGame();
		}
	}

	@Override
	public void onStopGame() {
		for (MushGameEventListener listener : listeners) {
			listener.onStopGame();
		}
	}

	@Override
	public void onAddPlayer(GameUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		for (MushGameEventListener listener : listeners) {
			listener.onAddPlayer(user);
		}
	}

	@Override
	public void onPerformAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		for (MushGameEventListener listener : listeners) {
			listener.onPerformAction(action);
		}
	}

	@Override
	public void onTimeoutNotification(Phase currentPhase, long missingTime) {
		if (currentPhase == null) {
			throw new IllegalArgumentException();
		}
		for (MushGameEventListener listener : listeners) {
			listener.onTimeoutNotification(currentPhase, missingTime);
		}
	}

	@Override
	public void onError(MushGameError error) {
		if (error == null) {
			throw new IllegalArgumentException();
		}
		for (MushGameEventListener listener : listeners) {
			listener.onError(error);
		}
	}
}

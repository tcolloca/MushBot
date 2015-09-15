package chatmushbot.util;

import mush.game.error.AlreadyPlayingError;
import mush.game.error.InvalidCreateError;
import mush.game.error.InvalidJoinError;
import mush.game.error.InvalidStartError;
import mush.game.error.InvalidStopError;
import mush.game.error.MushGameError;
import mush.game.error.NotEnoughPlayersError;
import mush.game.error.NotEnoughPlayersInGameError;
import util.message.Message;

/**
 * This class finds for each Mush Game Error the correct message with it's
 * parameters.
 * 
 * @author Tomas
 */
public class ErrorManager implements MushErrorValues {

	ErrorManager() {
	}

	/**
	 * Returns the {@link Message} that corresponds to the {@link MushGameError}
	 * .
	 * 
	 * @param error
	 * @return the {@link Message} that corresponds to the {@link MushGameError}
	 *         .
	 * @throws IllegalArgumentException
	 *             If {@code error} is null, or if there is no message for the
	 *             given error.
	 */
	public Message getMessage(MushGameError error) {
		if (error == null) {
			throw new IllegalArgumentException();
		}
		if (error instanceof InvalidCreateError) {
			return new Message(MUSH_CREATE_ALREADY);
		}
		if (error instanceof AlreadyPlayingError) {
			return new Message(MUSH_JOIN_ALREADY);
		}
		if (error instanceof InvalidJoinError) {
			return new Message(MUSH_JOIN_INVALID);
		}
		if (error instanceof InvalidStartError) {
			return new Message(MUSH_START_INVALID);
		}
		if (error instanceof InvalidStopError) {
			return new Message(MUSH_STOP_INVALID);
		}
		if (error instanceof NotEnoughPlayersError) {
			NotEnoughPlayersError e = (NotEnoughPlayersError) error;
			return new Message(MUSH_REQUIRED_PLAYERS, e.getRequiredPlayers(),
					e.getMinMush());
		}
		if (error instanceof NotEnoughPlayersInGameError) {
			NotEnoughPlayersInGameError e = (NotEnoughPlayersInGameError) error;
			return new Message(MUSH_REQUIRED_PLAYERS_WITH_TRIES, e.getRequiredPlayers(),
					e.getMinMush(), e.getTryNumber());
		}
		throw new IllegalArgumentException();
	}
}

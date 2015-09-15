package mush.game.error;

import mush.game.GameUser;

public class AlreadyPlayingError extends MushGameError {

	public AlreadyPlayingError(GameUser user) {
		super(user);
	}

}

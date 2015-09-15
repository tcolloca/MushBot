package mush.game.error;

import mush.game.GameUser;

public class InvalidStartError extends MushGameError {

	public InvalidStartError(GameUser user) {
		super(user);
	}

}

package mush.game.error;

import mush.game.GameUser;

public class InvalidStopError extends MushGameError {

	public InvalidStopError(GameUser user) {
		super(user);
	}
}

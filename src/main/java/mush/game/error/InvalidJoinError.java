package mush.game.error;

import mush.game.GameUser;

public class InvalidJoinError extends MushGameError {

	public InvalidJoinError(GameUser user) {
		super(user);
	}

}

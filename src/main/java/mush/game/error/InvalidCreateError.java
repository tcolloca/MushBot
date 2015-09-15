package mush.game.error;

import mush.game.GameUser;

public class InvalidCreateError extends MushGameError {

	public InvalidCreateError(GameUser gameUser) {
		super(gameUser);
	}

}

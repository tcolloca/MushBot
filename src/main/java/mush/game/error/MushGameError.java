package mush.game.error;

import mush.game.GameUser;

public class MushGameError {

	private GameUser user;

	public MushGameError() {
	}

	public MushGameError(GameUser user) {
		this.user = user;
	}

	public GameUser getUser() {
		return user;
	}
}

package mush.game.error;

import mush.game.GameUser;

public class NotEnoughPlayersError extends MushGameError {

	private int requiredPlayers;
	private int minMush;

	public NotEnoughPlayersError(GameUser user, int requiredPlayers, int minMush) {
		super(user);
		this.requiredPlayers = requiredPlayers;
		this.minMush = minMush;
	}

	public int getRequiredPlayers() {
		return requiredPlayers;
	}

	public int getMinMush() {
		return minMush;
	}
}

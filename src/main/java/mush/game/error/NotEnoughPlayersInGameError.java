package mush.game.error;

/**
 * Error triggered when there are not enough players to start a Game, and the
 * game tried to start it.
 * 
 * @author Tomas
 */
public class NotEnoughPlayersInGameError extends MushGameError {

	private int tryNumber;
	private int requiredPlayers;
	private int minMush;

	public NotEnoughPlayersInGameError(int tryNumber, int requiredPlayers,
			int minMush) {
		super();
		this.tryNumber = tryNumber;
		this.requiredPlayers = requiredPlayers;
		this.minMush = minMush;
	}

	/**
	 * Returns the amount of times that the game tried to start a game.
	 * 
	 * @return the amount of times that the game tried to start a game.
	 */
	public int getTryNumber() {
		return tryNumber;
	}

	/**
	 * Returns the required number of players to start a game.
	 * 
	 * @return the required number of players to start a game.
	 */
	public int getRequiredPlayers() {
		return requiredPlayers;
	}

	/**
	 * Returns the minimum amount of Mush of the current configuration.
	 * 
	 * @return the minimum amount of Mush of the current configuration.
	 */
	public int getMinMush() {
		return minMush;
	}
}

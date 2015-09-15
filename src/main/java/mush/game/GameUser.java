package mush.game;

/**
 * Represents a user that is playing or is going to play a Mush Game.
 * 
 * @author Tomas
 */
public interface GameUser {

	/**
	 * Returns an id of the user.
	 * 
	 * @return an id of the user.
	 */
	public Object getId();

	/**
	 * Returns the user's username.
	 * 
	 * @return the user's username.
	 */
	public String getUsername();
}

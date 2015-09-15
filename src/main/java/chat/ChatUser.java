package chat;

/**
 * Represents a chat's user.
 * 
 * @author Tomas
 */
public interface ChatUser {

	/**
	 * Returns the id associated with the user.
	 * 
	 * @return the id associated with the user.
	 */
	public Object getId();

	/**
	 * Returns the username of the user.
	 * 
	 * @return the username of the user.
	 */
	public String getUsername();

	/**
	 * Sends a message to the user.
	 * 
	 * @param message
	 * @throws SendMessageNotAllowedException
	 *             If the client cannot send messages to the user.
	 */
	public void sendMessage(String message)
			throws SendMessageNotAllowedException;

	/**
	 * Invites the user to the channel.
	 * 
	 * @param channel
	 * @throws ChatActionNotAllowedException
	 *             If the client cannot invite the user to the channel.
	 * @throws UnsupportedOperationException
	 *             If the user does not support invitations to channels.
	 */
	public void inviteTo(ChatChannel channel)
			throws ChatActionNotAllowedException;
}

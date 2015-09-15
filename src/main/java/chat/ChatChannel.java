package chat;

/**
 * Represents a chat's channel.
 * 
 * @author Tomas
 */
public interface ChatChannel {

	/**
	 * Returns the id associated with the channel.
	 * 
	 * @return the id associated with the channel.
	 */
	public Object getId();

	/**
	 * Returns the name of the channel.
	 * 
	 * @return the name of the channel.
	 */
	public String getName();

	/**
	 * Sends a message to the channel.
	 * 
	 * @param message
	 * @throws SendMessageNotAllowedException
	 *             If the client cannot send messages to the channel.
	 * @throws IllegalArgumentException
	 *             If the message received is null.
	 */
	public void sendMessage(String message)
			throws SendMessageNotAllowedException;

	/**
	 * Silences the channel.
	 * 
	 * @throws ChatActionNotAllowedException
	 *             If the client cannot silence the channel.
	 * @throws UnsupportedOperationException
	 *             If the channel does not support silencing.
	 */
	public void silence() throws ChatActionNotAllowedException;

	/**
	 * Sets the channel as invite-only. This means that users can enter the
	 * channel only if they have been invited.
	 * 
	 * @throws ChatActionNotAllowedException
	 *             If the client cannot set the channel to invite-only.
	 * @throws UnsupportedOperationException
	 *             If the channel does not support invite-only option.
	 */
	public void setInviteOnly() throws ChatActionNotAllowedException;

	/**
	 * Sets the channel as secret. This means that users can't know of the
	 * existence of the channel unless they are invited to it or they are
	 * already on it.
	 * 
	 * @throws ChatActionNotAllowedException
	 *             If the client cannot set the channel to secret.
	 * @throws UnsupportedOperationException
	 *             If the channel does not support secret option.
	 */
	public void setSecret() throws ChatActionNotAllowedException;
}

package chat.event;

import chat.ChatChannel;
import chat.ChatUser;

/**
 * Listener of chat events.
 * 
 * @author Tomas
 */
public interface ChatEventListener {

	/**
	 * Does something when a message is being sent by an user on a channel.
	 * 
	 * @param channel
	 *            Channel where the message is being sent.
	 * @param user
	 *            User that sent the message.
	 * @param message
	 * @throws IllegalArgumentException
	 *             If any parameter is null.
	 */
	public void onMessage(ChatChannel channel, ChatUser user, String message);

	/**
	 * Does something when a message is being sent by an user privately.
	 * 
	 * @param user
	 *            User that sent the message.
	 * @param message
	 * @throws IllegalArgumentException
	 *             If any parameter is null.
	 */
	public void onPrivateMessage(ChatUser user, String message);
}

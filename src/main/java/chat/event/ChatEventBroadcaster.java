package chat.event;

import java.util.HashSet;
import java.util.Set;

import chat.ChatChannel;
import chat.ChatUser;

/**
 * An event broadcaster for both messages sent through a channel and private
 * messages.
 * 
 * @author Tomas
 */
public class ChatEventBroadcaster implements ChatEventListener {

	private Set<ChatEventListener> listeners;

	public ChatEventBroadcaster() {
		listeners = new HashSet<ChatEventListener>();
	}

	/**
	 * Subscribes the listener received to all chat events.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If {@code listener} is null.
	 */
	public void subscribe(ChatEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void onMessage(ChatChannel channel, ChatUser user, String message) {
		if (channel == null || user == null || message == null) {
			throw new IllegalArgumentException();
		}
		for (ChatEventListener listener : listeners) {
			listener.onMessage(channel, user, message);
		}
	}

	@Override
	public void onPrivateMessage(ChatUser user, String message) {
		if (user == null || message == null) {
			throw new IllegalArgumentException();
		}
		for (ChatEventListener listener : listeners) {
			listener.onPrivateMessage(user, message);
		}
	}
}

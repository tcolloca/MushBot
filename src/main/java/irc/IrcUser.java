package irc;

import org.pircbotx.User;

import chat.ChatChannel;
import chat.ChatUser;

/**
 * A Proxy for PircBotX User that implements {@link ChatUser}.
 * 
 * @author Tomas
 */
public class IrcUser implements ChatUser {

	private static final String NEW_LINE = "\r\n";

	private User user;

	/**
	 * @param user
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	IrcUser(User user) {
		this.user = user;
	}

	@Override
	public Object getId() {
		return user.getLogin() + "@" + user.getHostmask();
	}

	@Override
	public String getUsername() {
		return user.getNick();
	}

	@Override
	public void sendMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			user.send().message(line);
		}
	}

	@Override
	public void inviteTo(ChatChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		user.send().invite(((IrcChannel) channel).getName());
	}
}

package irc;

import org.pircbotx.Channel;

import chat.ChatActionNotAllowedException;
import chat.ChatChannel;

/**
 * A Proxy for PircBotX Channel that implements {@link ChatChannel}.
 * 
 * @author Tomas
 */
public class IrcChannel implements ChatChannel {

	private static final String VOICE_ONLY = "+m";
	private static final String NEW_LINE = "\r\n";

	private Channel channel;

	/**
	 * @param channel
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	IrcChannel(Channel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
	}

	@Override
	public Object getId() {
		return channel.getName();
	}

	@Override
	public String getName() {
		return channel.getName();
	}

	@Override
	public void sendMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			channel.send().message(line);
		}
	}

	@Override
	public void silence() throws ChatActionNotAllowedException {
		if (!channel.isOp(channel.getBot().getUserBot())) {
			throw new ChatActionNotAllowedException();
		}
		channel.send().setMode(VOICE_ONLY);
	}

	@Override
	public void setInviteOnly() throws ChatActionNotAllowedException {
		if (!channel.isOp(channel.getBot().getUserBot())) {
			throw new ChatActionNotAllowedException();
		}
		channel.send().setInviteOnly(channel);
	}

	@Override
	public void setSecret() throws ChatActionNotAllowedException {
		if (!channel.isOp(channel.getBot().getUserBot())) {
			throw new ChatActionNotAllowedException();
		}
		channel.send().setSecret(channel);
	}
}

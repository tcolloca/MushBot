package bot.ircbot;

import chat.Channel;

public class IrcChannel implements Channel {

	private static final String VOICE_ONLY = "+m";
	private static final String NEW_LINE = "\r\n";

	private org.pircbotx.Channel channel;

	IrcChannel(org.pircbotx.Channel channel) {
		this.channel = channel;
	}

	public void sendMessage(String message) {
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			channel.send().message(line);
		}
	}

	public void silence() {
		channel.send().setMode(VOICE_ONLY);
	}

	org.pircbotx.Channel getChannel() {
		return channel;
	}

	public void setInviteOnly() {
		channel.send().setInviteOnly(channel);
	}

	public void setSecret() {
		channel.send().setSecret(channel);
	}

	public String getName() {
		return channel.getName();
	}
}

package bot.ircbot;

import chat.Channel;
import chat.User;

public class IrcUser implements User {

	private static final String NEW_LINE = "\r\n";
	
	private org.pircbotx.User user;

	IrcUser(org.pircbotx.User user) {
		this.user = user;
	}

	public String getNick() {
		return user.getNick();
	}

	public void sendMessage(String message) {
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			user.send().message(line);
		}
	}

	public void inviteTo(Channel channel) {
		user.send().invite(((IrcChannel) channel).getChannel());
	}
}

package bot.ircbot;

import java.util.HashMap;
import java.util.Map;

import chat.Channel;
import chat.User;

public class ChatBuilder {

	private Map<org.pircbotx.Channel, Channel> channels;
	private Map<org.pircbotx.User, User> users;

	ChatBuilder() {
		channels = new HashMap<org.pircbotx.Channel, Channel>();
		users = new HashMap<org.pircbotx.User, User>();
	}

	Channel buildChannel(org.pircbotx.Channel channel) {
		if (!channels.containsKey(channel)) {
			channels.put(channel, new IrcChannel(channel));	
		}
		return channels.get(channel);
	}

	User buildUser(org.pircbotx.User user) {
		if (!users.containsKey(user)) {
			users.put(user, new IrcUser(user));
		}
		return users.get(user);
	}
}

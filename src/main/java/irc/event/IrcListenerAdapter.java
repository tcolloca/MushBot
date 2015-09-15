package irc.event;

import irc.IrcBot;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;

import properties.ConnectionProperties;
import chat.ChatChannel;
import chat.ChatUser;
import chat.NoSuchChannelException;
import chat.NoSuchUserException;
import chat.event.ChatEventBroadcaster;

/**
 * Listener for all IRC Chat Events using PircBotX events.
 * 
 * @author Tomas
 */
public class IrcListenerAdapter extends ListenerAdapter<PircBotX> {

	private static final String JOIN_COMMAND_PART = "IN";
	private static final String UNKNOWN_COMMAND = "Unknown command";

	private PircBotX pircBot;
	private IrcBot ircBot;
	private ChatEventBroadcaster chatEventBroadcaster;

	/**
	 * @param ircBot
	 * @throws IllegalArgumentException
	 *             If {@code ircBot} is null.
	 */
	public IrcListenerAdapter(IrcBot ircBot) {
		if (ircBot == null) {
			throw new IllegalArgumentException();
		}
		this.ircBot = ircBot;
		chatEventBroadcaster = ircBot.getChatEventBroadcaster();
		chatEventBroadcaster.subscribe(ircBot);
	}

	public void setPircBot(PircBotX pircBot) {
		this.pircBot = pircBot;
	}

	@Override
	public void onServerResponse(ServerResponseEvent<PircBotX> event)
			throws Exception {
		if (event.getRawLine().contains(JOIN_COMMAND_PART)
				&& event.getRawLine().contains(UNKNOWN_COMMAND)) {
			if (pircBot == null) {
				throw new IllegalArgumentException();
			}
			pircBot.sendIRC().joinChannel(ConnectionProperties.channel());
		}
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
		ircBot.joinToChannel(event.getChannel());
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		ChatChannel channel = getIrcChannel(event.getChannel());
		ChatUser user = getIrcUser(event.getUser());
		String message = event.getMessage();
		chatEventBroadcaster.onMessage(channel, user, message);
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event)
			throws Exception {
		ChatUser user = getIrcUser(event.getUser());
		String message = event.getMessage();
		chatEventBroadcaster.onPrivateMessage(user, message);
	}

	private ChatUser getIrcUser(User user) {
		try {
			return ircBot.getUser(user.getLogin() + "@" + user.getHostmask());
		} catch (NoSuchUserException e) {
			throw new IllegalStateException();
		}
	}

	private ChatChannel getIrcChannel(Channel channel) {
		try {
			return ircBot.getChannel(channel.getName());
		} catch (NoSuchChannelException e) {
			throw new IllegalStateException();
		}
	}
}

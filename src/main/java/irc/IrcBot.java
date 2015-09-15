package irc;

import irc.event.IrcListenerAdapter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;

import properties.ConnectionProperties;
import chat.ChatBot;
import chat.ChatChannel;
import chat.ChatUser;
import chat.NoSuchChannelException;
import chat.NoSuchUserException;
import chat.command.ChatCommandFactory;
import chat.parser.ChatCommandParser;

/**
 * This class is an implementation of {@link ChatBot} for an IRC Chat using
 * PircBotX framework.
 * 
 * @author Tomas
 */
public class IrcBot extends ChatBot {

	private PircBotX pircBot;
	private Map<Object, IrcUser> users;
	private Map<Object, IrcChannel> channels;

	/**
	 * Creates the IrcBot and sets the listener for all the IRC Chat Events.
	 * 
	 * @param commandFactory
	 * @param parser
	 */
	public IrcBot(ChatCommandFactory commandFactory, ChatCommandParser parser) {
		super(commandFactory, parser);
		users = new HashMap<Object, IrcUser>();
		channels = new HashMap<Object, IrcChannel>();
		IrcListenerAdapter listener = new IrcListenerAdapter(this);
		pircBot = new PircBotX(getConfiguration(listener));
		listener.setPircBot(pircBot);
		(new Thread(new Runnable() {

			public void run() {
				try {
					pircBot.startBot();
				} catch (IOException e) {
					throw new ConnectionException();
				} catch (IrcException e) {
					throw new ConnectionException();
				}
			}

		})).start();
	}

	protected void connectionException() throws ConnectionException {
		throw new ConnectionException();
	}

	/**
	 * Returns {@code true} if it has already logged in.
	 * 
	 * @return {@code true} if it has already logged in.
	 */
	public boolean hasLoggedIn() {
		return !pircBot.getNick().isEmpty();
	}

	/**
	 * Joins the PircBotX Channel received, and creates a new IrcChannel for it.
	 * 
	 * @param channel
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	public void joinToChannel(Channel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		channels.put(channel.getName(), new IrcChannel(channel));
	}

	@Override
	public ChatChannel getChannel(Object channelId)
			throws NoSuchChannelException {
		if (channelId == null) {
			throw new NoSuchChannelException();
		}
		if (!hasJoinedToChannel(channelId)) {
			pircBot.sendIRC().joinChannel(channelId.toString());
			while (!hasJoinedToChannel(channelId)) {
				sleep();
			}
		}
		return channels.get(channelId);
	}

	@Override
	public ChatUser getUser(Object userId) throws NoSuchUserException {
		if (userId == null) {
			throw new NoSuchUserException();
		}
		if (!users.containsKey(userId)) {
			User user = findUser(userId);
			if (user == null) {
				throw new NoSuchUserException();
			}
			users.put(userId, new IrcUser(user));
		}
		return users.get(userId);
	}

	private User findUser(Object userId) {
		for (User user : pircBot.getUserChannelDao().getAllUsers()) {
			if ((user.getLogin() + "@" + user.getHostmask()).equals(userId)) {
				return user;
			}
		}
		return null;
	}

	private boolean hasJoinedToChannel(Object channelId) {
		return channels.containsKey(channelId);
	}

	private Configuration<PircBotX> getConfiguration(
			ListenerAdapter<PircBotX> listener) {
		Configuration<PircBotX> configuration = new Configuration.Builder<PircBotX>()
				.setName(ConnectionProperties.nickname())
				.setAutoReconnect(true)
				.setNickservPassword(ConnectionProperties.password())
				.setServerHostname(ConnectionProperties.server())
				.setEncoding(Charset.forName(ConnectionProperties.encoding()))
				.addListener(listener).buildConfiguration();
		return configuration;
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}
}

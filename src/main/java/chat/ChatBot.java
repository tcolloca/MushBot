package chat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chat.command.ChatCommand;
import chat.command.ChatCommandFactory;
import chat.event.ChatEventBroadcaster;
import chat.event.ChatEventListener;
import chat.parser.ChatCommandParser;

/**
 * A chat bot that interprets commands and executes them. It can also perform
 * different chat actions, such as sending messages, silencing a channel, etc.
 * <p>
 * When this class is extended then someone must ask for the
 * {@link ChatEventBroadcaster} subscribe to it, and call it's methods when the different events
 * occur.
 * </p>
 * 
 * @author Tomás
 */
public abstract class ChatBot implements ChatEventListener {

	private Set<ChatChannel> channels;
	private ChatCommandFactory commandFactory;
	private ChatCommandParser parser;
	private ChatEventBroadcaster chatEventBroadcaster;

	/**
	 * Returns a {@link ChatChannel} associated with the channelId received.
	 * 
	 * @param channelId
	 * @return a {@link ChatChannel} associated with the channelId received.
	 * @throws NoSuchChannelException
	 *             If there is no channel associated with the channelId.
	 */
	public abstract ChatChannel getChannel(Object channelId)
			throws NoSuchChannelException;

	/**
	 * Returns a {@link ChatUser} associated with the userId received.
	 * 
	 * @param userId
	 * @return a {@link ChatUser} associated with the userId received.
	 * @throws NoSuchUserException
	 *             If there is no user associated with the userId.
	 */
	public abstract ChatUser getUser(Object userId) throws NoSuchUserException;

	/**
	 * Constructs a {@code ChatBot} and a new {@link ChatEventBroadcaster} to
	 * which it subscribes.
	 * 
	 * @param commandFactory
	 * @param parser
	 * @throws IllegalArgumentException
	 *             If any parameter is null.
	 */
	public ChatBot(ChatCommandFactory commandFactory, ChatCommandParser parser) {
		this(commandFactory, parser, null);
	}

	/**
	 * Constructs a {@code ChatBot} it subscribes to the
	 * {@code chatEventBroadcaster}. If it is null a new
	 * {@link ChatEventBroadcaster} is created.
	 * 
	 * @param commandFactory
	 * @param parser
	 * @param chatEventBroadcaster
	 *            If it is null a new {@link ChatEventBroadcaster} is created.
	 * @throws IllegalArgumentException
	 *             If {@code commandFactory} or {@code parser} are null.
	 */
	public ChatBot(ChatCommandFactory commandFactory, ChatCommandParser parser,
			ChatEventBroadcaster chatEventBroadcaster) {
		if (commandFactory == null || parser == null) {
			throw new IllegalArgumentException();
		}
		this.channels = new HashSet<ChatChannel>();
		this.commandFactory = commandFactory;
		this.parser = parser;
		if (chatEventBroadcaster == null) {
			this.chatEventBroadcaster = new ChatEventBroadcaster();
		} else {
			this.chatEventBroadcaster = chatEventBroadcaster;
		}
	}

	/**
	 * Sends a message to all the channels that the bot has joined.
	 * 
	 * @param message
	 * @throws SendMessageNotAllowedException
	 *             If the bot cannot send messages to the channel.
	 * @throws IllegalArgumentException
	 *             If {@code message} is null.
	 */
	public void send(String message) throws SendMessageNotAllowedException {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		for (ChatChannel channel : channels) {
			send(channel, message);
		}
	}

	/**
	 * Sends a message to a channel.
	 * 
	 * @param channel
	 * @param message
	 * @throws SendMessageNotAllowedException
	 *             If the bot cannot send messages to the channel.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void send(ChatChannel channel, String message)
			throws SendMessageNotAllowedException {
		if (channel == null || message == null) {
			throw new IllegalArgumentException();
		}
		channel.sendMessage(message);
	}

	/**
	 * Sends a private message to an user.
	 * 
	 * @param user
	 * @param message
	 * @throws SendMessageNotAllowedException
	 *             If the bot cannot send messages to the user.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void send(ChatUser user, String message)
			throws SendMessageNotAllowedException {
		if (user == null || message == null) {
			throw new IllegalArgumentException();
		}
		user.sendMessage(message);
	}

	/**
	 * Invites an user to a channel.
	 * 
	 * @param user
	 * @param channel
	 * @throws ChatActionNotAllowedException
	 *             If the bot cannot invite the user to the channel.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void inviteToChannel(ChatUser user, ChatChannel channel)
			throws ChatActionNotAllowedException {
		if (user == null || channel == null) {
			throw new IllegalArgumentException();
		}
		user.inviteTo(channel);
	}

	/**
	 * Joins and returns the channel associated with the channel id received. If
	 * there is no channel associated to the received id it throws
	 * {@code NoSuchChannelException}.
	 * 
	 * @param channelId
	 * @return the channel that has been joined.
	 * @throws NoSuchChannelException
	 *             If there is no channel associated to {@code channelId}.
	 * @throws IllegalArgumentException
	 *             If {@code channelId} is null.
	 */
	public ChatChannel joinChannel(Object channelId)
			throws NoSuchChannelException {
		if (channelId == null) {
			throw new IllegalArgumentException();
		}
		ChatChannel channel;
		channel = getChannel(channelId);
		channels.add(channel);
		return channel;
	}

	/**
	 * Silences the channel received.
	 * 
	 * @param channel
	 * @throws ChatActionNotAllowedException
	 *             If the bot cannot send messages to the channel.
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	public void silenceChannel(ChatChannel channel)
			throws ChatActionNotAllowedException {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		channel.silence();
	}

	@Override
	public void onMessage(ChatChannel channel, ChatUser user, String message) {
		if (channel == null || user == null || message == null) {
			throw new IllegalArgumentException();
		}
		if (parser.isCommand(message)) {
			ChatCommand command = getChatCommand(message, user);
			if (command != null) {
				command.execute(channel);
			}
		}
	}

	@Override
	public void onPrivateMessage(ChatUser user, String message) {
		if (user == null || message == null) {
			throw new IllegalArgumentException();
		}
		if (parser.isCommand(message)) {
			ChatCommand command = getChatCommand(message, user);
			if (command != null) {
				command.execute(user);
			}
		}
	}

	public ChatCommandFactory getCommandFactory() {
		return commandFactory;
	}

	public ChatCommandParser getParser() {
		return parser;
	}

	public ChatEventBroadcaster getChatEventBroadcaster() {
		return chatEventBroadcaster;
	}

	private ChatCommand getChatCommand(String message, ChatUser executer) {
		List<String> args = parser.parse(message);
		return commandFactory.build(args.get(0), args.subList(1, args.size()),
				executer);
	}
}

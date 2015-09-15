package chatmushbot;

import i18n.Internationalizable;
import irc.IrcBot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mush.MushBot;
import mush.game.action.GameAction;
import mush.game.action.player.PlayerAction;
import properties.DefaultProperties;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chat.NoSuchUserException;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.command.game.initial.CreateCommand;
import chatmushbot.command.game.initial.JoinCommand;
import chatmushbot.command.game.initial.StartCommand;
import chatmushbot.command.game.initial.StopCommand;
import chatmushbot.command.game.player.GameCommand;
import chatmushbot.command.util.ChatMushBotCommandManager;
import chatmushbot.parser.ChatMushBotCommandParser;
import chatmushbot.security.AuthManager;
import chatmushbot.util.ErrorValues;
import chatmushbot.util.MushChannelNotCreatedException;
import chatmushbot.util.MushGameNarrator;

/**
 * Bot that hosts different Mush Games for each channel implemented for IRC
 * Chat. It contains reference to all the different MushBots associated to each
 * channel.
 * 
 * @author Tomas
 */
public class ChatMushBot extends IrcBot implements Internationalizable,
		ErrorValues {

	private Map<ChatChannel, MushBot> mushBotsMap;
	private Map<ChatUser, MushBot> userMushBotsMap;
	private AuthManager authManager;
	private String language;

	public ChatMushBot() {
		super(new ChatMushBotCommandFactory(), new ChatMushBotCommandParser());
		((ChatMushBotCommandFactory) getCommandFactory()).setChatMushBot(this);
		mushBotsMap = new HashMap<ChatChannel, MushBot>();
		userMushBotsMap = new HashMap<ChatUser, MushBot>();
		authManager = new AuthManager(this);
		language = DefaultProperties.language();
	}

	/**
	 * Creates a new {@link MushBot} for the given channel, and commands it to
	 * create a new game.
	 * 
	 * @param channel
	 *            Channel where a new game will be created.
	 * @param user
	 *            User that executed the {@link CreateCommand}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void createGame(ChatChannel channel, ChatUser user) {
		if (channel == null || user == null) {
			throw new IllegalArgumentException();
		}
		if (!mushBotsMap.containsKey(channel)) {
			initGame(channel);
		}
		getMushBot(channel).createGame(new ChatGameUser(user));
	}

	/**
	 * Commands the {@link MushBot} to start the Mush Game associated with the
	 * channel.
	 * 
	 * @param channel
	 * @param action
	 *            {@link GameAction} associated with the {@link StartCommand}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void startGame(ChatChannel channel, GameAction action) {
		if (channel == null || action == null) {
			throw new IllegalArgumentException();
		}
		getMushBot(channel).performAction(action);
	}

	/**
	 * Commands the {@link MushBot} to stop the Mush Game associated with the
	 * channel.
	 * 
	 * @param channel
	 * @param action
	 *            {@link GameAction} associated with the {@link StopCommand}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void stopGame(ChatChannel channel, GameAction action) {
		if (channel == null || action == null) {
			throw new IllegalArgumentException();
		}
		getMushBot(channel).performAction(action);
		destroyGame(channel);
	}

	/**
	 * Returns if there is any Mush Game associated with the given channel.
	 * 
	 * @param channel
	 * @return if there is any Mush Game associated with the given channel.
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	public boolean hasGame(ChatChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		return mushBotsMap.containsKey(channel);
	}

	/**
	 * Returns if there the given user is playing any Mush Game.
	 * 
	 * @param user
	 * @return if there the given user is playing any Mush Game.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public boolean hasGame(ChatUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		return userMushBotsMap.containsKey(user);
	}

	/**
	 * Commands the {@link MushBot} to add to the Mush Game associated with the
	 * channel the player contained in the {@link GameAction}.
	 * 
	 * @param channel
	 * @param action
	 *            {@link GameAction} associated with the {@link JoinCommand}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void addPlayer(ChatChannel channel, GameAction action) {
		if (channel == null || action == null) {
			throw new IllegalArgumentException();
		}
		try {
			ChatUser user = getUser((String) ((PlayerAction) action)
					.getPerformer().getId());
			if (hasGame(user) && getMushBot(user).hasEnded()) {
				System.out.println("llegue!");
				userMushBotsMap.remove(user);
			}
			if (!hasGame(user)) {
				userMushBotsMap.put(getUser((String) ((PlayerAction) action)
						.getPerformer().getId()), getMushBot(channel));
			}
			getMushBot(user).performAction(action);
		} catch (NoSuchUserException e) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Commands the {@link MushBot} to perform the {@link GameAction} over the
	 * Mush Game associated with the user that performed the action.
	 * 
	 * @param user
	 * @param action
	 *            {@link GameAction} associated with a {@link GameCommand}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void performAction(ChatUser user, GameAction action) {
		getMushBot(user).performAction(action);
	}

	/**
	 * Returns the list of all available commands in the current language of the
	 * bot.
	 * 
	 * @return the list of all available commands in the current language of the
	 *         bot.
	 */
	public Set<String> getAvailableCommands() {
		return ChatMushBotCommandManager.getAvailableCommands(language);
	}
	
	@Override
	public void joinToChannel(org.pircbotx.Channel channel) {
		super.joinToChannel(channel);
		authManager.initializeUsers();
	}

	@Override
	public ChatUser getUser(Object userId) throws NoSuchUserException {
		return authManager.getUser(super.getUser(userId));
	}

	@Override
	public boolean hasLanguage(String language) {
		if (language == null) {
			throw new IllegalArgumentException();
		}
		return Messages.hasLanguage(language);
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(String language) {
		if (language == null || !hasLanguage(language)) {
			throw new IllegalArgumentException();
		}
		this.language = language;
	}

	@Override
	public Set<String> getAvailableLanguages() {
		return Messages.getAvailableLanguages();
	}

	private void initGame(ChatChannel channel) {
		MushBot mushBot = new MushBot();
		MushGameNarrator narrator;
		try {
			narrator = new MushGameNarrator(this, channel);
			mushBotsMap.put(channel, mushBot);
			mushBot.addMushGameEventListener(narrator);
		} catch (MushChannelNotCreatedException e) {
			send(channel,
					Messages.getMessage(language, MUSH_CHANNEL_NOT_CREATED));
		}
	}

	private void destroyGame(ChatChannel channel) {
		Iterator<Entry<ChatUser, MushBot>> it = userMushBotsMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<ChatUser, MushBot> e = it.next();
			if (e.getValue().equals(getMushBot(channel))) {
				it.remove();
			}
		}
		mushBotsMap.remove(channel);
	}

	private MushBot getMushBot(ChatChannel channel) {
		if (!mushBotsMap.containsKey(channel)) {
			throw new IllegalStateException();
		}
		return mushBotsMap.get(channel);
	}

	private MushBot getMushBot(ChatUser user) {
		if (!userMushBotsMap.containsKey(user)) {
			throw new IllegalStateException();
		}
		return userMushBotsMap.get(user);
	}
}

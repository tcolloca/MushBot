package bot;

import java.util.List;

import mush.MushGame;
import mush.ai.ChannelHandler;
import mush.properties.GameProperties;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import parser.CommandParser;
import properties.ConnectionProperties;
import util.MessagesManager;

import command.CommandsManager;
import command.IrcBotCommand;

@SuppressWarnings("rawtypes")
public class MushBot extends ListenerAdapter implements IrcBot {

	private static final String JOIN_COMMAND_PART = "IN";
	private static final String UNKNOWN_COMMAND = "Unknown command";
	private static final String NEW_LINE = "\r\n";

	private PircBotX bot;
	private Channel mainChannel;

	private CommandParser parser;
	private MessagesManager messagesManager;

	private MushGame mushGame;
	private GameProperties gameProperties;

	public MushBot() {
		parser = new CommandParser();
		messagesManager = new MessagesManager();
	}

	public void setBot(PircBotX bot) {
		this.bot = bot;
	}

	public void setChannel(Channel channel) {
		this.mainChannel = channel;
	}

	public void onJoin(JoinEvent event) throws Exception {
		if (mainChannel == null) {
			mainChannel = event.getChannel();
			bot.sendIRC().identify(ConnectionProperties.password());
		}
		if (event.getChannel().getName().contains(MushGame.MUSH_CHANNEL_PREFIX)) {
			mushGame.setMushChannel(event.getChannel());
		}
	}

	@Override
	public void onServerResponse(ServerResponseEvent event) throws Exception {
		if (event.getRawLine().contains(JOIN_COMMAND_PART)
				&& event.getRawLine().contains(UNKNOWN_COMMAND)) {
			bot.sendIRC().joinChannel(ConnectionProperties.channel());
		}
	}

	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {
		String message = event.getMessage();
		if (parser.isCommand(message)) {
			IrcBotCommand command = parser.parse(event.getMessage());
			command.execute(this, (GenericMessageEvent) event);
		}
	}

	public String getMessage(String key) {
		return messagesManager.get(key);
	}

	public String getMessage(String key, List<String> args) {
		return messagesManager.get(key, args != null ? args.toArray() : null);
	}

	public void sendMessage(Channel channel, String message) {
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			channel.send().message(line);
		}
	}

	public void sendResourceMessage(String key) {
		sendResourceMessage(mainChannel, key);
	}

	public void sendResourceMessage(Channel channel, String key) {
		sendMessage(channel, getMessage(key));
	}

	public void sendResourceMessage(String key, List<String> args) {
		sendResourceMessage(mainChannel, key, args);
	}

	public void sendResourceMessage(Channel channel, String key,
			List<String> args) {
		sendMessage(channel, getMessage(key, args));
	}

	public void sendPrivateMessage(User user, String message) {
		String[] lines = message.split(NEW_LINE);
		for (String line : lines) {
			user.send().message(line);
		}
	}

	public void sendPrivateResourceMessage(User user, String key) {
		sendPrivateMessage(user, getMessage(key));
	}

	public void sendPrivateResourceMessage(User user, String key,
			List<String> args) {
		sendPrivateMessage(user, getMessage(key, args));
	}

	public void changeLanguage(String lang) {
		messagesManager.changeLanguage(lang);
	}

	public List<String> getAvailableCommands() {
		return CommandsManager.getAvailableCommands(messagesManager
				.getLanguage());
	}

	public void joinChannel(String channel) {
		bot.sendIRC().joinChannel(channel);
	}

	public void silenceChannel() {
		silenceChannel(mainChannel);
	}

	public void silenceChannel(Channel channel) {
		ChannelHandler.silenceAll(channel);
	}

	public void inviteToChannel(Channel channel, List<User> users) {
		while (channel == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		ChannelHandler.inviteToChannel(channel, users);
	}

	public MushGame getMushGame() {
		return mushGame;
	}

	public void createGame() {
		mushGame = new MushGame(this, gameProperties);
	}

	public void addPlayer(User user) {
		mushGame.addPlayer(user);
	}

	public void startGame() {
		mushGame.startGame();
	}

	public void mushVote(User user, String string) {
		mushGame.vote(user, string);
	}

	public void endGame() {
		mushGame = null;
	}
}

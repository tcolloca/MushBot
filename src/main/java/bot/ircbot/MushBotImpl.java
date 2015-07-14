package bot.ircbot;

import java.util.List;

import mush.command.ActionCommandType;
import mush.game.MushGame;
import mush.properties.GameProperties;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import properties.ConnectionProperties;
import properties.DefaultProperties;
import util.message.MessagePack;
import util.parser.CommandParser;
import chat.Channel;
import chat.MushBot;
import chat.User;
import chat.command.BotCommand;
import chat.command.CommandsManager;

@SuppressWarnings("rawtypes")
public class MushBotImpl extends ListenerAdapter implements MushBot {

	private static final String JOIN_COMMAND_PART = "IN";
	private static final String UNKNOWN_COMMAND = "Unknown command";

	private PircBotX bot;
	private Channel mainChannel;
	private ChatBuilder chatBuilder;

	private GameProperties gameProperties;

	private String language;
	private MushGame mushGame;

	public MushBotImpl() {
		language = DefaultProperties.language();
		gameProperties = new GameProperties();
		chatBuilder = new ChatBuilder();
	}

	public void setBot(PircBotX bot) {
		this.bot = bot;
	}

	/*** ListenerAdapter Methods ***/

	public void onJoin(JoinEvent event) throws Exception {
		if (mainChannel == null) {
			bot.sendIRC().identify(ConnectionProperties.password());
		}
		addChannel(event.getChannel());
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
		if (CommandParser.isCommand(message)) {
			BotCommand command = CommandParser.parse(event.getMessage());
			User user = chatBuilder.buildUser(event.getUser());
			command.execute(this, user);
		}
	}

	private void addChannel(org.pircbotx.Channel ircChannel) {
		Channel channel = chatBuilder.buildChannel(ircChannel);
		if (mainChannel == null) {
			mainChannel = channel;
		}
		if (channel.getName().contains(MushGame.MUSH_CHANNEL_PREFIX)) {
			if (mushGame != null) {
				mushGame.setMushChannel(channel);
			}
		}
	}

	/*** Bot methods ***/

	public void send(String message) {
		mainChannel.sendMessage(message);
	}

	public void send(Channel channel, String message) {
		channel.sendMessage(message);
	}

	public void send(User user, String message) {
		user.sendMessage(message);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getAvailableCommands() {
		return CommandsManager.getAvailableCommands(language);
	}

	public void joinChannel(String channelName) {
		bot.sendIRC().joinChannel(channelName);
	}

	public void silenceChannel(Channel channel) {
		channel.silence();
	}

	public void inviteToChannel(User user, Channel channel) {
		user.inviteTo(channel);
	}

	/*** MushBot methods ***/

	public void createGame(Object id) {
		mushGame = new MushGame(this, gameProperties, (Channel) id);
	}

	public void addPlayer(Object id, User user) {
		getMushGame(id).addPlayer(user);
	}

	public void startGame(Object id) {
		getMushGame(id).startGame();
	}

	public void endGame(Object id) {
		mushGame = null;
	}

	public boolean isGameCreated(Object id) {
		return getMushGame(id) != null && getMushGame(id).hasStarted();
	}

	public boolean canGameBeJoined(Object id) {
		return getMushGame(id) == null || !getMushGame(id).isInJoiningPhase();
	}

	public boolean isPlaying(User user) {
		return mushGame != null && mushGame.isPlaying(user);
	}

	public boolean canGameBeStarted(Object id) {
		return getMushGame(id) == null;
	}

	public boolean isGameStarted(Object id) {
		return !getMushGame(id).isInJoiningPhase();
	}

	private MushGame getMushGame(Object id) {
		return mushGame;
	}

	public boolean canPerformAction(Object id, User user,
			ActionCommandType actionCommandType) {
		return getMushGame(id) != null && getMushGame(id).canPerformAction(user, actionCommandType);
	}

	public boolean isCorrectTime(Object id, ActionCommandType actionCommandType) {
		return getMushGame(id).isCorrectTime(actionCommandType);
	}

	public void performAction(Object id, User user,
			ActionCommandType actionCommandType, List<String> args) {
		getMushGame(id).performAction(user, actionCommandType, args);
	}

	public MessagePack getActionErrors(Object id, User user, ActionCommandType actionCommandType,
			List<String> args) {
		return getMushGame(id).getActionErrors(user, actionCommandType, args);
	}

	public Channel getMainChannel() {
		return mainChannel;
	}
}

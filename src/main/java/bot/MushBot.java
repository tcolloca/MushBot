package bot;

import java.util.List;

import mush.GameProperties;
import mush.MushGame;
import mush.ai.ChannelHandler;
import mush.ai.Narrator;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import parser.CommandParser;
import properties.ConnectionProperties;
import util.CommandNameManager;
import util.MessagesManager;
import util.MessagesValues;
import util.StringConverter;

import com.google.common.collect.Lists;
import command.IrcBotCommand;

@SuppressWarnings("rawtypes")
public class MushBot extends ListenerAdapter implements IrcBot, MessagesValues {

	private PircBotX bot;
	private Channel mainChannel;
	private Channel mushChannel;
	private CommandParser parser;
	private MessagesManager messagesManager;
	private Narrator narrator;
	private MushGame mushGame;

	public MushBot() {
		parser = new CommandParser();
		messagesManager = new MessagesManager();
		narrator = new Narrator(this);
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
		if (event.getChannel().getName()
				.contains(GameProperties.mushChannelPrefix())) {
			mushChannel = event.getChannel();
			ChannelHandler.prepareMushChannel(mushChannel);
		}
	}

	@Override
	public void onServerResponse(ServerResponseEvent event) throws Exception {
		if ((event.getRawLine().contains("IN") || event.getRawLine().contains(
				"OIN"))
				&& event.getRawLine().contains("Unknown command")) {
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
		String[] lines = message.split("\r\n");
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
		try {
			String[] lines = message.split("\r\n");
			for (String line : lines) {
				user.send().message(line);
			}
		} catch (Exception e) {
			// TODO Do sth
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

	public boolean hasLanguage(String lang) {
		return messagesManager.hasLanguage(lang);
	}

	public void showAllLanguages(User user) {
		String s;
		List<String> availableLanguages = messagesManager
				.getAvailableLanguages();
		if (availableLanguages.isEmpty()) {
			sendPrivateResourceMessage(user, LANG_NO_AVL_LANGS);
		} else if (availableLanguages.size() == 1) {
			sendPrivateResourceMessage(user, LANG_ONE_AVL_LANG,
					Lists.newArrayList("\"" + availableLanguages.get(0) + "\""));
		} else {
			s = StringConverter.stringfyList(availableLanguages.subList(0,
					availableLanguages.size() - 1), "\"");
			String lastLanguage = "\""
					+ availableLanguages.get(availableLanguages.size() - 1)
					+ "\"";
			sendPrivateResourceMessage(user, LANG_AVL_LANGS,
					Lists.newArrayList(s, lastLanguage));
		}
	}

	public String getAvailableCommandsString() {
		List<String> availableCommands = CommandNameManager
				.getAvailableCommands(messagesManager.getCurrentLanguage());
		return StringConverter.stringfyList(availableCommands, "\"");
	}

	public void joinChannel(String channel) {
		bot.sendIRC().joinChannel(channel);
	}

	public void silenceAll(Channel channel) {
		ChannelHandler.silenceAll(channel);
	}

	public void silenceMainChannel() {
		ChannelHandler.silenceAll(mainChannel);
	}

	public void silenceMushChannel() {
		ChannelHandler.silenceAll(mushChannel);
	}

	public void createGame(User user) {
		if (mushGame != null && mushGame.hasStarted()) {
			narrator.announceGameAlreadyCreated(user);
		} else {
			mushGame = new MushGame(this, narrator);
		}
	}

	public void addPlayer(User user) {
		if (mushGame == null || !mushGame.isInJoiningPhase()) {
			narrator.announceInvalidJoining(user);
		} else if (mushGame.isPlaying(user)) {
			narrator.annouceAlreadyJoined(user);
		} else {
			mushGame.addPlayer(user);
		}
	}

	public void startGame(User user) {
		if (mushGame == null) {
			narrator.announceInvalidStart(user);
		} else if (!mushGame.isInJoiningPhase()) {
			narrator.announceGameAlreadyStarted(user);
		} else {
			mushGame.startGame();
		}
	}

	public void mushVote(User user, String string) {
		if (mushGame == null || !mushGame.isPlaying(user)) {
			narrator.announceNotAllowedGameAction(user);
		} else if (!mushGame.isMush(user)) {
			narrator.announceNotAllowedMushAction(user);
		} else if (!mushGame.isInMushAttackPhase()) {
			narrator.announceNotMushAttackTime(user);
		} else {
			mushGame.vote(user, string);
		}
	}

	public void inviteToMushChannel(List<User> users) {
		while (mushChannel == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		ChannelHandler.inviteToChannel(mushChannel, users);
	}

	public void endGame() {
		mushGame = null;
	}

	public void announceMushVoteResult(User electedUser) {
		narrator.announceVoteResult(mushChannel, electedUser);
	}
}

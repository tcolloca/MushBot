package bot;

import java.util.List;

import mush.MushGame;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.UnknownEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import parser.CommandParser;
import util.CommandNameManager;
import util.MessagesManager;
import util.MessagesValues;
import util.StringConverter;

import com.google.common.collect.Lists;
import command.IrcBotCommand;

@SuppressWarnings("rawtypes")
public class MushBot extends ListenerAdapter implements IrcBot, MessagesValues {

	private PircBotX bot;
	private Channel channel;
	private CommandParser parser;
	private MessagesManager messagesManager;
	private MushGame mushGame;

	public MushBot() {
		parser = new CommandParser();
		messagesManager = new MessagesManager();
	}

	public void setBot(PircBotX bot) {
		this.bot = bot;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void onJoin(JoinEvent event) throws Exception {
		if (channel == null) {
			channel = event.getChannel();
		}
	}

	@Override
	public void onUnknown(UnknownEvent event) throws Exception {
		// System.out.println("ENTERING");
		bot.getInputParser().handleLine("irc.uk.mibbit.net 421 mushBot JOIN");
	}

	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {
		String message = event.getMessage();
		if (parser.isCommand(message)) {
			IrcBotCommand command = parser.parse(event.getMessage());
			command.execute(this, (GenericMessageEvent) event);
		}
	}

	public void sendMessage(String message) {
		String[] lines = message.split("\r\n");
		for (String line : lines) {
			channel.send().message(line);
		}
	}

	public void sendResourceMessage(String key) {
		sendMessage(messagesManager.get(key));
	}

	public void sendResourceMessage(String key, List<String> args) {
		sendMessage(messagesManager.get(key, args != null ? args.toArray()
				: null));
	}

	public void sendPrivateMessage(User user, String message) {
		try {
			user.send().message(message);
		} catch (Exception e) {
			// TODO Do sth
		}
	}

	public void sendPrivateResourceMessage(User user, String key) {
		sendPrivateMessage(user, messagesManager.get(key));
	}

	public void sendPrivateResourceMessage(User user, String key,
			List<String> args) {
		sendPrivateMessage(user,
				messagesManager.get(key, args != null ? args.toArray() : null));
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

	public void createGame(User user) {
		if (mushGame != null && mushGame.hasStarted()) {
			mushGame.getNarrator().announceGameAlreadyCreated(user);
		} else {
			mushGame = new MushGame(this);
		}
	}

	public void addPlayer(User user) {
		if (mushGame == null || !mushGame.isInJoiningPhase()) {
			mushGame.getNarrator().announceInvalidJoining(user);
		} else if (mushGame.isPlaying(user)) {
			mushGame.getNarrator().annouceAlreadyJoined(user);
		} else {
			mushGame.addPlayer(user);
		}
	}

	public void startGame(User user) {
		if (mushGame == null) {
			mushGame.getNarrator().announceInvalidStart(user);
		} else if (!mushGame.isInJoiningPhase()) {
			mushGame.getNarrator().announceGameAlreadyStarted(user);
		} else {
			mushGame.startGame();
		}
	}

	public void mushVote(User user, String string) {
		if (mushGame == null || !mushGame.isPlaying(user)) {
			mushGame.getNarrator().announceNotAllowedGameAction(user);
		} else if (!mushGame.isMush(user)) {
			mushGame.getNarrator().announceNotAllowedMushAction(user);
		} else if (!mushGame.isInMushAttackPhase()) {
			mushGame.getNarrator().announceNotMushAttackTime(user);
		} else {
			mushGame.vote(user, string);
		}
	}
}

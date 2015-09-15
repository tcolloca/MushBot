package chatmushbot.command;

import java.util.ArrayList;
import java.util.List;

import util.StringConverter;
import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.util.ChatMushBotCommandManager;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that changes the language of the bot to the one passed as parameter
 * if it is a valid one. If it is executed without parameters it displays a list
 * of available languages.
 * 
 * @author Tomas
 */
public class LangCommand extends ChatMushBotCommand {

	private static final String LANG_NO_LANG = "lang_no_lang";
	private static final String LANG_INVALID = "lang_invalid";
	private static final String LANG_CHANGED = "lang_changed";
	private static final String LANG_NO_AVL_LANGS = "lang_no_avl_langs";
	private static final String LANG_ONE_AVL_LANG = "lang_one_avl_lang";
	private static final String LANG_AVL_LANGS = "lang_avl_langs";

	private static final String HELP_LANG = "help_lang";
	private static final String HELP_LANG_ALL = "help_lang_all";
	private static final String HELP_LANG_LANG = "help_lang_lang";

	private static final int LANGUAGE_INDEX = 0;

	public LangCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	public void safeExecute(ChatChannel channelr) {
		execute(getExecuter());
	}

	@Override
	public void safeExecute(ChatUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		if (getParameters().isEmpty()) {
			noLanguage(user);
		} else if (ChatMushBotCommandManager.isParameter(ParameterName.ALL,
				getParameters().get(LANGUAGE_INDEX))) {
			showAllAvailableLanguages(user);
		} else if (!getChatMushBot().hasLanguage(
				getParameters().get(LANGUAGE_INDEX))) {
			invalidLanguage(user);
		} else {
			changeLanguage(getParameters().get(LANGUAGE_INDEX));
		}
	}

	@Override
	protected Message getHelp(ChatUser user) {
		if (getParameters().isEmpty()) {
			return new Message(HELP_LANG);
		} else if (ChatMushBotCommandManager.isParameter(ParameterName.ALL,
				getParameters().get(LANGUAGE_INDEX))) {
			return new Message(HELP_LANG_ALL);
		} else {
			return new Message(HELP_LANG_LANG, getParameters().get(
					LANGUAGE_INDEX));
		}
	}

	private void noLanguage(ChatUser user) {
		getChatMushBot().send(user,
				Messages.getMessage(getLang(), LANG_NO_LANG));
	}

	private void showAllAvailableLanguages(ChatUser user) {
		String s;
		List<String> availableLanguages = new ArrayList<String>(
				Messages.getAvailableLanguages());
		if (availableLanguages.isEmpty()) {
			getChatMushBot().send(user,
					Messages.getMessage(getLang(), LANG_NO_AVL_LANGS));
		} else if (availableLanguages.size() == 1) {
			getChatMushBot().send(
					user,
					Messages.getMessage(getLang(), LANG_ONE_AVL_LANG, "\""
							+ availableLanguages + "\""));
		} else {
			s = StringConverter.stringfyList(availableLanguages.subList(0,
					availableLanguages.size() - 1), "\"");
			String lastLanguage = "\""
					+ availableLanguages.get(availableLanguages.size() - 1)
					+ "\"";
			getChatMushBot().send(
					user,
					Messages.getMessage(getLang(), LANG_AVL_LANGS, s,
							lastLanguage));
		}
	}

	private void invalidLanguage(ChatUser user) {
		getChatMushBot().send(user,
				Messages.getMessage(getLang(), LANG_INVALID, getParameters()));
	}

	private void changeLanguage(String language) {
		getChatMushBot().setLanguage(language);
		getChatMushBot().send(
				Messages.getMessage(getLang(), LANG_CHANGED, getParameters()));
	}
}

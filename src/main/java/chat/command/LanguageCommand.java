package chat.command;

import java.util.ArrayList;
import java.util.List;

import util.StringConverter;
import util.message.BotMessagesManager;
import util.message.MessagePack;
import util.message.ResourceBundlesManager;
import bot.Bot;
import chat.User;

public class LanguageCommand extends BotCommand {

	LanguageCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		if (args.size() <= 1) {
			noLanguage(bot, user);
		} else if (CommandsManager.isCommand(CommandName.ALL, args.get(1))) {
			showAllAvailableLanguages(bot, user);
		} else if (!ResourceBundlesManager.hasLanguage(args.get(1))) {
			invalidLanguage(bot, user);
		} else {
			changeLanguage(bot, args.get(1));
		}
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		if (args.size() == 1) {
			return new MessagePack(HELP_LANG);
		} else if (CommandsManager.isCommand(CommandName.ALL, args.get(1))) {
			return new MessagePack(HELP_LANG_ALL);
		} else {
			return new MessagePack(HELP_LANG_LANG, args.get(1));
		}
	}

	private void noLanguage(Bot bot, User user) {
		bot.send(user, BotMessagesManager.get(bot, LANG_NO_LANG));
	}

	private void showAllAvailableLanguages(Bot bot, User user) {
		String s;
		List<String> availableLanguages = new ArrayList<String>(
				ResourceBundlesManager.getAvailableLanguages());
		if (availableLanguages.isEmpty()) {
			bot.send(user, BotMessagesManager.get(bot, LANG_NO_AVL_LANGS));
		} else if (availableLanguages.size() == 1) {
			bot.send(
					user,
					BotMessagesManager.get(bot, LANG_ONE_AVL_LANG, "\""
							+ availableLanguages + "\""));
		} else {
			s = StringConverter.stringfyList(availableLanguages.subList(0,
					availableLanguages.size() - 1), "\"");
			String lastLanguage = "\""
					+ availableLanguages.get(availableLanguages.size() - 1)
					+ "\"";
			bot.send(user, BotMessagesManager.get(bot, LANG_AVL_LANGS, s,
					lastLanguage));
		}
	}

	private void invalidLanguage(Bot bot, User user) {
		bot.send(user, BotMessagesManager.get(bot, LANG_INVALID, args));
	}

	private void changeLanguage(Bot bot, String language) {
		bot.setLanguage(language);
		bot.send(BotMessagesManager.get(bot, LANG_CHANGED, args));
	}
}

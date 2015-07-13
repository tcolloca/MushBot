package command;

import java.util.ArrayList;
import java.util.List;

import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.ResourceBundlesManager;
import util.StringConverter;
import bot.IrcBot;

import com.google.common.collect.Lists;

public class LanguageCommand extends IrcBotCommand {

	LanguageCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		User user = event.getUser();
		if (args.size() <= 1) {
			noLanguage(bot, user);
		} else if (CommandsManager.isCommand(CommandName.ALL, args.get(1))) {
			showAllAvailableLanguages(bot, event.getUser());
		} else if (!ResourceBundlesManager.hasLanguage(args.get(1))) {
			invalidLanguage(bot, user);
		} else {
			changeLanguage(bot, user);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(HELP_LANG);
		} else if (CommandsManager.isCommand(CommandName.ALL, args.get(1))) {
			return new MessagePack(HELP_LANG_ALL);
		} else {
			return new MessagePack(HELP_LANG_LANG, Lists.newArrayList(args
					.get(1)));
		}
	}

	private void noLanguage(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, LANG_NO_LANG);
	}

	private void showAllAvailableLanguages(IrcBot bot, User user) {
		String s;
		List<String> availableLanguages = new ArrayList<String>(
				ResourceBundlesManager.getAvailableLanguages());
		if (availableLanguages.isEmpty()) {
			bot.sendPrivateResourceMessage(user, LANG_NO_AVL_LANGS);
		} else if (availableLanguages.size() == 1) {
			bot.sendPrivateResourceMessage(user, LANG_ONE_AVL_LANG,
					Lists.newArrayList("\"" + availableLanguages + "\""));
		} else {
			s = StringConverter.stringfyList(availableLanguages.subList(0,
					availableLanguages.size() - 1), "\"");
			String lastLanguage = "\""
					+ availableLanguages.get(availableLanguages.size() - 1)
					+ "\"";
			bot.sendPrivateResourceMessage(user, LANG_AVL_LANGS,
					Lists.newArrayList(s, lastLanguage));
		}
	}

	private void invalidLanguage(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, LANG_INVALID, args);
	}

	private void changeLanguage(IrcBot bot, User user) {
		bot.changeLanguage(args.get(1));
		bot.sendResourceMessage(LANG_CHANGED, args);
	}
}

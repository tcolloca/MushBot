package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.CommandNameManager;
import util.MessagePack;
import util.MessagesValues;
import bot.IrcBot;

import com.google.common.collect.Lists;
import command.help.HelpValues;

public class LanguageCommand extends IrcBotCommand implements MessagesValues {

	LanguageCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		if (args.size() <= 1) {
			bot.sendPrivateResourceMessage(event.getUser(), LANG_NO_LANG);
		} else if (CommandNameManager.isCommand(CommandName.ALL, args.get(1))) {
			bot.showAllLanguages(event.getUser());
		} else if (!bot.hasLanguage(args.get(1))) {
			bot.sendPrivateResourceMessage(event.getUser(), LANG_INVALID, args);
		} else {
			bot.changeLanguage(args.get(1));
			bot.sendResourceMessage(LANG_CHANGED, args);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(HelpValues.HELP_LANG);
		} else if (CommandNameManager.isCommand(CommandName.ALL, args.get(1))) {
			return new MessagePack(HelpValues.HELP_LANG_ALL);
		} else {
			return new MessagePack(HelpValues.HELP_LANG_LANG,
					Lists.newArrayList(args.get(1)));
		}
	}
}

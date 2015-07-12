package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.MessagesValues;
import bot.IrcBot;

import com.google.common.collect.Lists;
import command.help.HelpValues;

public class HelpCommand extends IrcBotCommand implements MessagesValues {

	HelpCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		if (args.size() <= 1) {
			bot.sendPrivateResourceMessage(event.getUser(), HELP,
					Lists.newArrayList(bot.getAvailableCommandsString()));
		} else {
			IrcBotCommand helpCommand = CommandFactory.build(args.get(1),
					args.subList(1, args.size()));
			MessagePack pack = helpCommand.getHelp(bot, event);
			bot.sendPrivateResourceMessage(event.getUser(), pack.getKey(), pack.getArgs());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(HelpValues.HELP_HELP);
		} else {
			IrcBotCommand helpCommand = CommandFactory.build(args.get(1),
					args.subList(1, args.size()));
			return new MessagePack(HelpValues.HELP_HELP_COMMAND,
					Lists.newArrayList(helpCommand.toString()));
		}
	}

}

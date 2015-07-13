package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.StringConverter;
import bot.IrcBot;

import com.google.common.collect.Lists;

public class HelpCommand extends IrcBotCommand {

	HelpCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		if (args.size() <= 1) {
			helpCommand(bot, event);
		} else {
			helpCommandWithArguments(bot, event);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(HELP_HELP);
		} else {
			IrcBotCommand helpCommand = CommandFactory.build(args.get(1),
					args.subList(1, args.size()));
			return new MessagePack(HELP_HELP_COMMAND,
					Lists.newArrayList(helpCommand.toString()));
		}
	}

	@SuppressWarnings("rawtypes")
	private void helpCommand(IrcBot bot, GenericMessageEvent event) {
		List<String> availableCommands = bot.getAvailableCommands();
		bot.sendPrivateResourceMessage(event.getUser(), HELP, Lists
				.newArrayList(StringConverter.stringfyList(availableCommands,
						"\"")));
	}

	@SuppressWarnings("rawtypes")
	private void helpCommandWithArguments(IrcBot bot, GenericMessageEvent event) {
		IrcBotCommand helpCommand = CommandFactory.build(args.get(1),
				args.subList(1, args.size()));
		MessagePack pack = helpCommand.getHelp(bot, event);
		bot.sendPrivateResourceMessage(event.getUser(), pack.getKey(),
				pack.getArgs());
	}
}

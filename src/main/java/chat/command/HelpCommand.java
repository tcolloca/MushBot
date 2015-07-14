package chat.command;

import java.util.List;

import util.StringConverter;
import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class HelpCommand extends BotCommand {

	HelpCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		if (args.size() <= 1) {
			helpCommand(bot, user);
		} else {
			helpCommandWithArguments(bot, user);
		}
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		if (args.size() == 1) {
			return new MessagePack(HELP_HELP);
		} else {
			BotCommand helpCommand = CommandFactory.build(args.get(1),
					args.subList(1, args.size()));
			return new MessagePack(HELP_HELP_COMMAND, helpCommand.toString());
		}
	}

	private void helpCommand(Bot bot, User user) {
		List<String> availableCommands = bot.getAvailableCommands();
		bot.send(
				user,
				BotMessagesManager.get(bot, HELP,
						StringConverter.stringfyList(availableCommands, "\"")));
	}

	private void helpCommandWithArguments(Bot bot, User user) {
		BotCommand helpCommand = CommandFactory.build(args.get(1),
				args.subList(1, args.size()));
		MessagePack pack = helpCommand.getHelp(bot, user);
		bot.send(user,
				BotMessagesManager.get(bot, pack.getKey(), pack.getArgs()));
	}
}

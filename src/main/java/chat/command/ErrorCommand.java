package chat.command;

import java.util.List;

import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class ErrorCommand extends BotCommand {

	ErrorCommand(List<String> args, String command) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		invalidCommand(bot, user);
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		return new MessagePack(COMMAND_INVALID, toString());
	}

	private void invalidCommand(Bot bot, User user) {
		bot.send(user, BotMessagesManager.get(bot, COMMAND_INVALID, toString()));
	}
}

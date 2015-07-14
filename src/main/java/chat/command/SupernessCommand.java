package chat.command;

import java.util.List;

import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class SupernessCommand extends BotCommand {

	SupernessCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		bot.send(BotMessagesManager.get(bot, SUPERNESS));
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		return new MessagePack(HELP_SUPERNESS);
	}
}

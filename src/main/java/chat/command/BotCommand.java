package chat.command;

import java.util.List;

import util.StringConverter;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public abstract class BotCommand implements CommandValues, HelpValues {

	protected List<String> args;

	protected BotCommand(List<String> args) {
		this.args = args;
	}

	public abstract void execute(Bot bot, User user);

	protected abstract MessagePack getHelp(Bot bot, User user);

	public String toString() {
		return StringConverter.stringfyList(args, " ", "", "");
	}
}

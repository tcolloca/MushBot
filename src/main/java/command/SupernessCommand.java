package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;

public class SupernessCommand extends IrcBotCommand {

	SupernessCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		bot.sendResourceMessage(SUPERNESS);
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HELP_SUPERNESS);
	}
}

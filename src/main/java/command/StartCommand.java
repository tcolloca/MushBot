package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;
import bot.MushBot;

public class StartCommand extends IrcBotCommand {

	StartCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		((MushBot) bot).startGame(event.getUser());
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HELP_START);
	}
}

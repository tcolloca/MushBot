package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.MessagesValues;
import bot.IrcBot;
import bot.MushBot;

import command.help.HelpValues;

public class CreateCommand extends IrcBotCommand implements MessagesValues {

	CreateCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		((MushBot) bot).createGame(event.getUser());
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HelpValues.HELP_CREATE);
	}
}

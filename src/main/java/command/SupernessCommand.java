package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.MessagesValues;
import bot.IrcBot;

import command.help.HelpValues;

public class SupernessCommand extends IrcBotCommand implements MessagesValues {

	SupernessCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		bot.sendResourceMessage(SUPERNESS_MESSAGE);
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HelpValues.HELP_SUPERNESS);
	}
}

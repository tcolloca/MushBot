package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.StringConverter;
import bot.IrcBot;

public abstract class IrcBotCommand implements CommandValues, HelpValues {

	protected List<String> args;

	IrcBotCommand(List<String> args) {
		this.args = args;
	}

	@SuppressWarnings("rawtypes")
	public abstract void execute(IrcBot bot, GenericMessageEvent event);

	@SuppressWarnings("rawtypes")
	abstract MessagePack getHelp(IrcBot bot, GenericMessageEvent event);

	public String toString() {
		return StringConverter.stringfyList(args, " ", "", "");
	}
}

package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
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
		if (args.isEmpty()) {
			return "";
		} else {
			String s = args.get(0);
			for (String arg : args.subList(1, args.size())) {
				s += " " + arg;
			}
			return s;
		}
	}
}

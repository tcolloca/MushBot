package command;

import java.util.List;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;

import com.google.common.collect.Lists;

public class ErrorCommand extends IrcBotCommand {

	ErrorCommand(List<String> args, String command) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		invalidCommand(bot, event);
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(COMMAND_INVALID, Lists.newArrayList(toString()));
	}

	@SuppressWarnings("rawtypes")
	private void invalidCommand(IrcBot bot, GenericMessageEvent event) {
		bot.sendPrivateResourceMessage(event.getUser(), COMMAND_INVALID,
				Lists.newArrayList(toString()));
	}
}

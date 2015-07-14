package chat.command.mush;

import java.util.List;

import mush.command.ActionCommandType;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class VoteCommand extends MushGameActionCommand {

	VoteCommand(List<String> args) {
		super(args, ActionCommandType.VOTE);
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getRole() {
		throw new IllegalStateException();
	}
}
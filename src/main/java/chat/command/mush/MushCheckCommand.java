package chat.command.mush;

import java.util.List;

import mush.command.ActionCommandType;
import mush.game.player.role.RoleValues;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class MushCheckCommand extends MushGameActionCommand {

	public MushCheckCommand(List<String> args) {
		super(args, ActionCommandType.MUSH_CHECK);
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		if (args.size() == 1) {
			return new MessagePack(MUSH_CHECK);
		} else {
			return new MessagePack(MUSH_CHECK_NICK, args.get(1));
		}
	}

	@Override
	String getRole() {
		return RoleValues.ROLE_ELEESHA;
	}
}

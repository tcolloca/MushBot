package chat.command.mush;

import java.util.List;

import mush.command.ActionCommandType;
import mush.game.player.role.RoleValues;
import util.message.MessagePack;
import bot.Bot;
import chat.User;

public class MushVoteCommand extends MushGameActionCommand {

	public MushVoteCommand(List<String> args) {
		super(args, ActionCommandType.MUSH_VOTE);
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		if (args.size() == 1) {
			return new MessagePack(MUSH_VOTE);
		} else {
			return new MessagePack(MUSH_VOTE_NICK, args.get(1));
		}
	}

	@Override
	String getRole() {
		return RoleValues.ROLE_MUSH;
	}
}

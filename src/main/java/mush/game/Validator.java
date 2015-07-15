package mush.game;

import java.util.List;

import mush.MushValues;
import mush.command.ActionCommandType;
import mush.game.player.role.RoleValues;
import util.message.MessagePack;
import chat.User;

public class Validator implements MushValues {

	private MushGame mushGame;

	Validator(MushGame mushGame) {
		this.mushGame = mushGame;
	}

	public boolean canPerformAction(User user,
			ActionCommandType actionCommandType) {
		switch (actionCommandType) {
		case MUSH_VOTE:
			return mushGame.isMush(user);
		case MUSH_CHECK:
			return mushGame.is(user, RoleValues.ROLE_ELEESHA);
		default:
			return true;
		}
	}

	public boolean isCorrectTime(ActionCommandType actionCommandType) {
		switch (actionCommandType) {
		case MUSH_VOTE:
			return mushGame.isInMushAttackPhase();
		case VOTE:
			return mushGame.isInVotingPhase();
		case MUSH_CHECK:
			return mushGame.isInMushAttackPhase();
		default:
			return false;
		}
	}

	public MessagePack getActionErrors(User user,
			ActionCommandType actionCommandType, List<String> args) {
		switch (actionCommandType) {
		case MUSH_CHECK:
			if (args.size() <= 1) {
				return new MessagePack(MUSH_CHECK_NO_NICK);
			}
			if (mushGame.getUser(args.get(1)) == null){
				return new MessagePack(MUSH_INVALID_PLAYER, args.get(1));
			}
			return null;
		case MUSH_VOTE:
		case VOTE:
			if (args.size() <= 1) {
				return new MessagePack(MUSH_VOTE_NO_NICK);
			}
			if (!mushGame.canVote(user)) {
				return new MessagePack(MUSH_VOTE_INVALID);
			}
			if (!mushGame.isVotable(args.get(1))) {
				return new MessagePack(MUSH_VOTE_UNKNOWN, args.get(1));
			}
			return null;
		default:
			return null;
		}
	}
}

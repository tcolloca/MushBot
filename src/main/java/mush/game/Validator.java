package mush.game;

import java.util.List;

import mush.MushValues;
import mush.command.ActionCommandType;
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
		default:
			return false;
		}
	}

	public MessagePack getActionErrors(User user,
			ActionCommandType actionCommandType, List<String> args) {
		switch (actionCommandType) {
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

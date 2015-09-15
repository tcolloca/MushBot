package mush.game;

import mush.MushValues;

public class Validator implements MushValues {

/*	private Game mushGame;

	Validator(Game mushGame) {
		this.mushGame = mushGame;
	}

	public boolean canPerformAction(ChatUser user,
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

	public Message getActionErrors(ChatUser user,
			ActionCommandType actionCommandType, List<String> args) {
		switch (actionCommandType) {
		case MUSH_VOTE:
		case VOTE:
			if (args.size() <= 1) {
				return new Message(MUSH_VOTE_NO_NICK);
			}
			if (!mushGame.canVote(user)) {
				return new Message(MUSH_VOTE_INVALID);
			}
			if (!mushGame.isVotable(args.get(1))) {
				return new Message(MUSH_VOTE_UNKNOWN, args.get(1));
			}
			return null;
		default:
			return null;
		}
	}*/
}

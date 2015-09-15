package chatmushbot.util;

import mush.game.action.GameAction;
import mush.game.action.player.MushvoteAction;
import mush.game.action.player.StartAction;
import mush.game.action.player.VoteAction;
import util.message.Message;

/**
 * This class finds for each Mush Game Action the correct message with it's
 * parameters.
 * 
 * @author Tomas
 */
public class ActionManager implements ActionValues {

	/**
	 * Returns the {@link Message} that corresponds to the {@link GameAction}.
	 * 
	 * @param action
	 * @return the {@link Message} that corresponds to the {@link GameAction}.
	 * @throws IllegalArgumentException
	 *             If {@code action} is null, or if there is no message for the
	 *             given action.
	 */
	public Message getMessage(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		if (action instanceof StartAction) {
			StartAction a = (StartAction) action;
			String mushAmountMessage = MUSH_MANY_MUSH;
			if (a.getMushAmount() == 1) {
				mushAmountMessage = MUSH_ONE_MUSH;
			}
			return new Message(mushAmountMessage, a.getPlayersAmount(),
					a.getMushAmount());
		}
		if (action instanceof MushvoteAction) {
			MushvoteAction a = (MushvoteAction) action;
			return new Message(MUSH_VOTE_VOTE, a.getPerformer(), a.getVoted());
		}
		if (action instanceof VoteAction) {
			VoteAction a = (VoteAction) action;
			return new Message(MUSH_VOTE_VOTE, a.getPerformer(), a.getVoted());
		}
		throw new IllegalArgumentException();
	}

	// TODO: Javadoc
	public boolean isPrivateAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		if (action instanceof MushvoteAction) {
			return false;
		}
		if (action instanceof VoteAction) {
			return false;
		}
		return false;
	}

	/**
	 * Returns {@code true} if the action can be performed only by a Mush.
	 * 
	 * @param action
	 * @return {@code true} if the action can be performed only by a Mush.
	 */
	public boolean isMushAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		if (action instanceof MushvoteAction) {
			return true;
		}
		if (action instanceof VoteAction) {
			return false;
		}
		return false;
	}
}

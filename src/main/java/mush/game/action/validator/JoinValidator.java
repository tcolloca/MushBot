package mush.game.action.validator;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.GameAction;
import mush.game.action.player.JoinAction;
import mush.game.error.AlreadyPlayingError;
import mush.game.error.InvalidJoinError;

public class JoinValidator implements Validator {

	public JoinValidator() {
	}

	public boolean validate(Game mushGame, GameAction gameAction) {
		JoinAction action = (JoinAction) gameAction;
		GameUser performer = action.getPerformer();
		if (mushGame.getPlayersMap().containsKey(performer.getId())) {
			mushGame.getEventMulticaster().onError(
					new AlreadyPlayingError(performer));
			return false;
		} else if (!mushGame.getStatus().canJoinToGame()) {
			mushGame.getEventMulticaster().onError(
					new InvalidJoinError(performer));
			return false;
		}
		return true;
	}
}

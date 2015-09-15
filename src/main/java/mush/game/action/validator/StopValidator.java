package mush.game.action.validator;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.GameAction;
import mush.game.action.player.StopAction;
import mush.game.error.InvalidStopError;

public class StopValidator implements Validator {

	public boolean validate(Game mushGame, GameAction gameAction) {
		StopAction action = (StopAction) gameAction;
		GameUser performer = action.getPerformer();
		if (!mushGame.getStatus().canStopGame()) {
			mushGame.getEventMulticaster().onError(
					new InvalidStopError(performer));
			return false;
		}
		return true;
	}
}

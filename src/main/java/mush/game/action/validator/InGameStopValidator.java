package mush.game.action.validator;

import mush.game.Game;
import mush.game.action.GameAction;

public class InGameStopValidator implements Validator {

	@Override
	public boolean validate(Game mushGame, GameAction action) {
		if (!mushGame.getStatus().canStopGame()) {
			return false;
		}
		return true;
	}

}

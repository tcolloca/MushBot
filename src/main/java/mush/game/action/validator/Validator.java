package mush.game.action.validator;

import mush.game.Game;
import mush.game.action.GameAction;

public interface Validator {

	public boolean validate(Game mushGame, GameAction action);
}

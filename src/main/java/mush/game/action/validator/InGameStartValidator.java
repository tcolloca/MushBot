package mush.game.action.validator;

import mush.game.Game;
import mush.game.action.GameAction;
import mush.game.error.NotEnoughPlayersInGameError;

/**
 * Validator for the StartAction when triggered by the game.
 * 
 * @author Tomas
 */
public class InGameStartValidator implements Validator {

	@Override
	public boolean validate(Game mushGame, GameAction gameAction) {
		if (!mushGame.getStatus().canStartGame()) {
			return false;
		}
		if (mushGame.getPlayers().size() < mushGame.getRequiredPlayers()) {
			mushGame.getEventMulticaster().onError(
					new NotEnoughPlayersInGameError(mushGame.getTimeoutTask()
							.getStartTryNumber(),
							mushGame.getRequiredPlayers(), mushGame
									.getGameProperties().getMinMushAmount()));
			return false;
		}
		return true;
	}

}

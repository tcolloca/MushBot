package mush.game.action.validator;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.GameAction;
import mush.game.action.player.StartAction;
import mush.game.error.InvalidStartError;
import mush.game.error.NotEnoughPlayersError;

public class StartValidator implements Validator {

	public StartValidator() {
	}

	public boolean validate(Game mushGame, GameAction gameAction) {
		StartAction action = (StartAction) gameAction;
		GameUser performer = action.getPerformer();
		if (!mushGame.getStatus().canStartGame()) {
			mushGame.getEventMulticaster().onError(
					new InvalidStartError(performer));
			return false;
		}
		if (mushGame.getPlayers().size() < mushGame.getRequiredPlayers()) {
			mushGame.getEventMulticaster().onError(
					new NotEnoughPlayersError(performer, mushGame
							.getRequiredPlayers(), mushGame.getGameProperties()
							.getMinMushAmount()));
			return false;
		}
		return true;
	}
}

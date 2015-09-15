package mush.game.action.player;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.validator.InGameStopValidator;
import mush.game.action.validator.StopValidator;

/**
 * Action that stops the current game if there is any.
 *
 * @author Tomas
 */
public class StopAction extends PlayerAction {

	public StopAction() {
		super(new InGameStopValidator());
	}

	public StopAction(GameUser performer) {
		super(performer, new StopValidator());
	}

	@Override
	void actionate(Game game) {
		game.getEventMulticaster().onStopGame();
		commit(game);
	}

	@Override
	public void commit(Game game) {
		game.getStatus().stop();
	}
}

package mush.game.action;

import mush.game.Game;

public interface GameAction {

	public void action(Game mushGame);

	public void commit(Game mushGame);
}

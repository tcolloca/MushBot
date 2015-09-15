package mush.game.action.player;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.validator.JoinValidator;
import mush.game.player.Player;

public class JoinAction extends PlayerAction {

	public JoinAction(GameUser performer) {
		super(performer, new JoinValidator());
	}

	@Override
	void actionate(Game game) {
		commit(game);
	}

	public void commit(Game mushGame) {
		mushGame.getPlayersMap().put((String) getPerformer().getId(),
				new Player());
		mushGame.getPrefixManager().addString(getPerformer().getUsername());
		mushGame.getEventMulticaster().onAddPlayer(getPerformer());
	}
}

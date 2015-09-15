package mush.game.action.player;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.validator.MushvoteValidator;

public class MushvoteAction extends PlayerAction {

	private String voted;

	public MushvoteAction(GameUser performer, String voted) {
		super(performer, new MushvoteValidator());
		this.voted = voted;
	}

	@Override
	void actionate(Game game) {
		// TODO
	}

	public void commit(Game mushGame) {
		// TODO Auto-generated method stub

	}

	public String getVoted() {
		return voted;
	}
}

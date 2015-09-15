package mush.game.action.player;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.validator.VoteValidator;

public class VoteAction extends PlayerAction {

	private String voted;

	public VoteAction(GameUser performer, String voted) {
		super(performer, new VoteValidator());
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

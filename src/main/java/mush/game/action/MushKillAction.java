package mush.game.action;

import mush.game.Game;
import mush.game.player.Player;
import util.message.Message;

public class MushKillAction extends Action {

	private Player victim;

	public MushKillAction(Player actioner, Player victim) {
		super(ActionType.MUSH_KILL, actioner);
		this.victim = victim;
	}

	public Player getVictim() {
		return victim;
	}

	@Override
	public void execute(Game game) {
		// TODO
		//	game.killPlayer(victim);
	}

	@Override
	public Message getHiddenMessagePack() {
		return new Message(ACTION_MUSH_KILL_HIDDEN, getPerformer()
				.getNick(), victim.getNick());
	}

	@Override
	public Message getVisibleMessagePack() {
		return new Message(ACTION_MUSH_KILL_VISIBLE, victim.getNick());
	}
}

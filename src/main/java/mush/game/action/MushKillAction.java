package mush.game.action;

import mush.game.MushGame;
import mush.game.player.Player;
import util.message.MessagePack;

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
	public void execute(MushGame game) {
		game.killPlayer(victim);
	}

	@Override
	public MessagePack getHiddenMessagePack() {
		return new MessagePack(ACTION_MUSH_KILL_HIDDEN, getPerformer()
				.getNick(), victim.getNick());
	}

	@Override
	public MessagePack getVisibleMessagePack() {
		return new MessagePack(ACTION_MUSH_KILL_VISIBLE, victim.getNick());
	}
}

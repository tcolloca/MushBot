package mush.action;

import mush.MushGame;
import mush.player.Player;
import util.MessagePack;

import com.google.common.collect.Lists;

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
		return new MessagePack(ACTION_MUSH_KILL_HIDDEN, Lists.newArrayList(
				getPerformer().getNick(), victim.getNick()));
	}

	@Override
	public MessagePack getVisibleMessagePack() {
		return new MessagePack(ACTION_MUSH_KILL_VISIBLE,
				Lists.newArrayList(victim.getNick()));
	}
}

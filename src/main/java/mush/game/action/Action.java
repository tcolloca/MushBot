package mush.game.action;

import mush.game.MushGame;
import mush.game.player.Player;
import util.message.MessagePack;

public abstract class Action implements Comparable<Action>, ActionValues {

	private ActionType type;
	private Player performer;

	public Action(ActionType type, Player performer) {
		super();
		this.type = type;
		this.performer = performer;
	}

	public abstract void execute(MushGame game);

	public abstract MessagePack getHiddenMessagePack();

	public Player getPerformer() {
		return performer;
	}

	public int compareTo(Action o) {
		return type.getValue() - o.type.getValue();
	}

	public final MessagePack getMessagePack() {
		if (isVisible()) {
			return getHiddenMessagePack();
		} else {
			return getVisibleMessagePack();
		}
	}

	public MessagePack getVisibleMessagePack() {
		return null;
	}

	public boolean isVisible() {
		return type.isVisible();
	}
}

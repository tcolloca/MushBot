package mush.game.action;

import mush.game.Game;
import mush.game.player.Player;
import util.message.Message;

public abstract class Action implements Comparable<Action>, ActionValues {

	private ActionType type;
	private Player performer;

	public Action(ActionType type, Player performer) {
		super();
		this.type = type;
		this.performer = performer;
	}

	public abstract void execute(Game game);

	public abstract Message getHiddenMessagePack();

	public Player getPerformer() {
		return performer;
	}

	public int compareTo(Action o) {
		return type.getValue() - o.type.getValue();
	}

	public final Message getMessagePack() {
		if (isVisible()) {
			return getHiddenMessagePack();
		} else {
			return getVisibleMessagePack();
		}
	}

	public Message getVisibleMessagePack() {
		return null;
	}

	public boolean isVisible() {
		return type.isVisible();
	}
}

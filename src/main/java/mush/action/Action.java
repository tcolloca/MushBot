package mush.action;

import mush.MushGame;
import mush.player.Player;
import util.MessagePack;

public abstract class Action implements Comparable<Action>, ActionValues {

	private ActionType type;
	private Player performer;

	public Action(ActionType type, Player performer) {
		super();
		this.type = type;
		this.performer = performer;
	}

	public abstract void execute(MushGame game);

	public abstract MessagePack getVisibleMessagePack();

	public Player getPerformer() {
		return performer;
	}

	public int compareTo(Action o) {
		return type.getValue() - o.type.getValue();
	}

	public final MessagePack getMessagePack() {
		switch (type.getVisibility()) {
		case VISIBLE:
			return getVisibleMessagePack();
		case HIDDEN:
			return getHiddenMessagePack();
		}
		return null;
	}

	public MessagePack getHiddenMessagePack() {
		return null;
	}

}

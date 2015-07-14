package mush.game.action;

public enum ActionType implements Comparable<ActionType> {

	MUSH_KILL(1, Visibility.HIDDEN);

	private int value;
	private Visibility visibility;

	private ActionType(int value, Visibility visibility) {
		this.value = value;
		this.visibility = visibility;
	}

	int getValue() {
		return value;
	}

	Visibility getVisibility() {
		return visibility;
	}

	boolean isVisible() {
		return visibility.isVisible();
	}
	
	boolean isHidden() {
		return visibility.isHidden();
	}
}

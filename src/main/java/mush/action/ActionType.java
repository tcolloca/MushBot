package mush.action;

public enum ActionType implements Comparable<ActionType> {

	MUSH_KILL(1, Visibility.HIDDEN);

	private int value;
	private Visibility visibility;

	private ActionType(int value, Visibility visibility) {
		this.value = value;
		this.visibility = visibility;
	}

	public int getValue() {
		return value;
	}

	public Visibility getVisibility() {
		return visibility;
	}
}

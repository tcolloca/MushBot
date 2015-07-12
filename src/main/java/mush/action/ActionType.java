package mush.action;

public enum ActionType implements Comparable<ActionType> {

	MUSH_KILL(1);

	private int value;

	private ActionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

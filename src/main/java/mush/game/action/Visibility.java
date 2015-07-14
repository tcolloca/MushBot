package mush.game.action;

public enum Visibility {

	VISIBLE, HIDDEN;

	boolean isVisible() {
		return this == VISIBLE;
	}

	boolean isHidden() {
		return this == HIDDEN;
	}
}

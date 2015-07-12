package command;

public enum CommandName {

	HELP("help"), HELLO("hello"), SUPERNESS("superness"), LANG("lang"), ALL(
			"all"), CREATE("create"), JOIN("join"), START("start"), MUSH_VOTE("mush_vote");

	private String name;

	private CommandName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

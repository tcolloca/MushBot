package chat.command;

public enum CommandName {

	HELP("help"), SUPERNESS("superness"), LANG("lang"), ALL(
			"all"), CREATE("create"), JOIN("join"), START("start"), MUSH_VOTE("mush_vote"), MUSH_CHECK("mush_check");

	private String name;

	private CommandName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

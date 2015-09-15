package chatmushbot.command;

import java.util.NoSuchElementException;

import security.Permission;

public enum CommandName implements Permission {

	HELP("help"), SUPERNESS("superness"), LANG("lang"), CREATE("create",
			".game.initial"), JOIN("join", ".game.initial"), START("start",
			".game.initial"), STOP("stop", ".game.initial"), MUSHVOTE(
			"mushvote", ".game.player"), ROLE("role", ".role");

	private String name;
	private String location;

	private CommandName(String name) {
		this(name, "");
	}

	private CommandName(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public static CommandName getByName(String name) {
		for (CommandName commandName : values()) {
			if (commandName.getName().equals(name)) {
				return commandName;
			}
		}
		throw new NoSuchElementException();
	}
}

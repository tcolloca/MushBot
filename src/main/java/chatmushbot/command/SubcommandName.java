package chatmushbot.command;

import java.util.NoSuchElementException;

import security.Permission;

public enum SubcommandName implements Permission {

	ROLE_GIVE("role_give", ".role"), ROLE_REMOVE("role_remove", ".role");

	private String name;
	private String location;

	private SubcommandName(String name) {
		this(name, "");
	}

	private SubcommandName(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public static SubcommandName getByName(String name) {
		for (SubcommandName subcommandName : values()) {
			if (subcommandName.getName().equals(name)) {
				return subcommandName;
			}
		}
		throw new NoSuchElementException();
	}
}

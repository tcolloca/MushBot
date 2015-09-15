package chatmushbot.command;

public enum ParameterName {

	ALL("all");

	private String name;

	private ParameterName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
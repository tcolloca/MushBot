package util;

import java.util.List;

public class MessagePack {

	private String key;
	private List<String> args;

	public MessagePack(String key) {
		this(key, null);
	}

	public MessagePack(String key, List<String> args) {
		this.key = key;
		this.args = args;
	}

	public String getKey() {
		return key;
	}

	public List<String> getArgs() {
		return args;
	}

}

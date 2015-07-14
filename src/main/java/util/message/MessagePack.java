package util.message;

import java.util.List;

import com.google.common.collect.Lists;

public class MessagePack {

	private String key;
	private List<String> args;

	public MessagePack(String key, String... strings) {
		this(key, Lists.newArrayList(strings));
	}

	public MessagePack(String key, List<String> args) {
		this.key = key;
		this.args = args;
	}

	public MessagePack(String key) {
		this.key = key;
		this.args = null;
	}

	public String getKey() {
		return key;
	}

	public List<String> getArgs() {
		return args;
	}

}

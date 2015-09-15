package util.message;

import java.util.ArrayList;
import java.util.List;

import util.StringConverter;

import com.google.common.collect.Lists;

/**
 * Class that wraps a key associated with a message of the resources files and
 * the arguments of it.
 * 
 * @author Tomas
 */
public class Message {

	private String key;
	private List<String> args;

	/**
	 * Creates a Message with the given key and converting into a list of
	 * strings the Objects received that will be used as arguments.
	 * 
	 * @param key
	 * @param objects
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public Message(String key, Object... objects) {
		if (key == null || objects == null) {
			throw new IllegalArgumentException();
		}
		this.key = key;
		args = new ArrayList<String>();
		for (Object o : objects) {
			if (o == null) {
				throw new IllegalArgumentException();
			}
			args.add(o.toString());
		}
	}

	/**
	 * Creates a Message with the given key and the given strings as arguments.
	 * 
	 * @param key
	 * @param strings
	 * @throws IllegalArgumentException
	 *             If {@code strings} is null.
	 */
	public Message(String key, String... strings) {
		this(key, Lists.newArrayList(strings));
		if (strings == null) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Creates a Message with the given key and the given list of arguments.
	 * 
	 * @param key
	 * @param args
	 * @throws IllegalArgumentException
	 *             If {@code args} is null.
	 */
	public Message(String key, List<String> args) {
		if (key == null || args == null) {
			throw new IllegalArgumentException();
		}
		this.key = key;
		this.args = args;
	}

	/**
	 * Creates a Message with the given key and no arguments.
	 * 
	 * @param key
	 * @throws IllegalArgumentException
	 *             If {@code key} is null.
	 */
	public Message(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		this.key = key;
		this.args = null;
	}

	String getKey() {
		return key;
	}

	List<String> getArgs() {
		return args;
	}

	public String toString() {
		return key + " - " + StringConverter.stringfyList(args);
	}
}

package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Converts a list of elements into a string using the different arguments of
 * the method. The default separator is ", ".
 * 
 * @author Tomas
 */
public class StringConverter {

	private static String DEFAULT_SEPARATOR = ", ";

	/**
	 * Returns a string with all the elements converted into String and
	 * separated with the {@code DEFAULT_SEPARATOR}.
	 * 
	 * @param list
	 * @return a string with all the elements converted into String and
	 *         separated with the {@code DEFAULT_SEPARATOR}.
	 * @throws IllegalArgumentException
	 *             If {@code list} is null.
	 */
	public static <T> String stringfyList(List<T> list) {
		return stringfyList(list, "");
	}

	/**
	 * Returns a string with all the elements converted into String and
	 * surrounded with the given enclosing and separated with the
	 * {@code DEFAULT_SEPARATOR}.
	 * 
	 * @param list
	 * @param enclosing
	 * @return a string with all the elements converted into String and
	 *         surrounded with the given enclosing and separated with the
	 *         {@code DEFAULT_SEPARATOR}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static <T> String stringfyList(List<T> list, String enclosing) {
		return stringfyList(list, enclosing, enclosing);
	}

	/**
	 * Returns a string with all the elements converted into String with preItem
	 * before them and postItem after them, and separated with the
	 * {@code DEFAULT_SEPARATOR}.
	 * 
	 * @param list
	 * @param enclosing
	 * @return a string with all the elements converted into String with preItem
	 *         before them and postItem after them, and separated with the
	 *         {@code DEFAULT_SEPARATOR}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static <T> String stringfyList(List<T> list, String preItem,
			String postItem) {
		return stringfyList(list, DEFAULT_SEPARATOR, preItem, postItem);
	}

	/**
	 * Returns a string with all the elements converted into String with preItem
	 * before them and postItem after them, and separated with the given
	 * separator.
	 * 
	 * @param list
	 * @param enclosing
	 * @return a string with all the elements converted into String with preItem
	 *         before them and postItem after them, and separated with the given
	 *         separator.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static <T> String stringfyList(List<T> list, String separator,
			String preItem, String postItem) {
		if (list.isEmpty()) {
			return "";
		}
		String s = preItem + list.get(0).toString() + postItem;
		for (T obj : list.subList(1, list.size())) {
			s += separator + preItem + obj.toString() + postItem;
		}
		return s;
	}

	/**
	 * Returns a string with all the elements converted into String and
	 * surrounded with the given enclosing and separated with the
	 * {@code DEFAULT_SEPARATOR}.
	 * 
	 * @param set
	 * @param enclosing
	 * @return a string with all the elements converted into String and
	 *         surrounded with the given enclosing and separated with the
	 *         {@code DEFAULT_SEPARATOR}.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static String stringfySet(Set<String> set, String enclosing) {
		return stringfyList(new ArrayList<String>(set), enclosing);
	}
}

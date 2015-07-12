package util;

import java.util.List;

public class StringConverter {

	public static <T> String stringfyList(List<T> list) {
		return stringfyList(list, "", "");
	}

	public static <T> String stringfyList(List<T> list, String enclosing) {
		return stringfyList(list, enclosing, enclosing);
	}

	public static <T> String stringfyList(List<T> list, String preItem,
			String postItem) {
		if (list.isEmpty()) {
			return "";
		}
		String s = preItem + list.get(0).toString() + postItem;
		for (T obj : list.subList(1, list.size())) {
			s += ", " + preItem + obj.toString() + postItem;
		}
		return s;
	}
}

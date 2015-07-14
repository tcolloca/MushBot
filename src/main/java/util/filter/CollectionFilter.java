package util.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionFilter {

	public static <T> Set<T> filter(Set<T> list, Filter<T> filter) {
		Set<T> newList = new HashSet<T>();
		for (T obj : list) {
			if (filter.evaluate(obj)) {
				newList.add(obj);
			}
		}
		return newList;
	}

	public static <T> List<T> filter(List<T> list, Filter<T> filter) {
		List<T> newList = new ArrayList<T>();
		for (T obj : list) {
			if (filter.evaluate(obj)) {
				newList.add(obj);
			}
		}
		return newList;
	}
}

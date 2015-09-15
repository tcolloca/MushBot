package util.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class allows to filter a Collection according to a {@link Filter}.
 * 
 * @author Tomas
 */
public class CollectionFilter {

	/**
	 * Filters a {@link Set} according to a filter.
	 * 
	 * @param set
	 *            Set to be filtered.
	 * @param filter
	 *            Filter that will be used to select the elements of the
	 *            {@code set}.
	 * @return a new {@link Set} with the elements of the original {@code set}
	 *         that passed the {@code filter}.
	 */
	public static <E> Set<E> filter(Set<E> set, Filter<E> filter) {
		Set<E> newSet = new HashSet<E>();
		for (E elem : set) {
			if (filter.evaluate(elem)) {
				newSet.add(elem);
			}
		}
		return newSet;
	}

	/**
	 * Filters a {@link List} according to a filter.
	 * 
	 * @param list
	 *            List to be filtered.
	 * @param filter
	 *            Filter that will be used to select the elements of the
	 *            {@code list}.
	 * @return a new {@link List} with the elements of the original {@code list}
	 *         that passed the {@code filter}.
	 */
	public static <E> List<E> filter(List<E> list, Filter<E> filter) {
		List<E> newList = new ArrayList<E>();
		for (E elem : list) {
			if (filter.evaluate(elem)) {
				newList.add(elem);
			}
		}
		return newList;
	}
}

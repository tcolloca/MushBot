package util.filter;

/**
 * Class that represents a function that determines if an element of type
 * {@code E} has certain characteristics or not.
 * 
 * @author Tomas
 *
 * @param <E>
 *            Type of the elements being filtered.
 */
public interface Filter<E> {

	/**
	 * Returns {@code true} if {@code elem} has certain characteristics.
	 * 
	 * @param elem
	 *            Element to be determined if it has certain characteristics or
	 *            not.
	 * @return {@code true} if {@code elem} has certain characteristics.
	 */
	public boolean evaluate(E elem);
}

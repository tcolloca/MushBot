package util.filter;

public interface Filter<T> {

	public boolean evaluate(T obj);
}

package util;

public interface Filter<T> {

	public boolean evaluate(T obj);
}

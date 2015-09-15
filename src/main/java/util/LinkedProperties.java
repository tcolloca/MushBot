package util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class LinkedProperties extends Properties {

	private static final long serialVersionUID = 1L;

	private Map<Object, Object> linkMap = new LinkedHashMap<Object, Object>();

	@Override
	public synchronized Object put(Object key, Object value) {
		return linkMap.put(key, value);
	}

	@Override
	public synchronized boolean contains(Object value) {
		return linkMap.containsValue(value);
	}

	@Override
	public boolean containsValue(Object value) {
		return linkMap.containsValue(value);
	}

	@Override
	public synchronized Enumeration<Object> elements() {
		throw new UnsupportedOperationException(
				"Enumerations are so old-school, don't use them, "
						+ "use keySet() or entrySet() instead");
	}

	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return linkMap.entrySet();
	}

	@Override
	public Set<Object> keySet() {
		return linkMap.keySet();
	}

	@Override
	public synchronized void clear() {
		linkMap.clear();
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		return linkMap.containsKey(key);
	}

	@Override
	public String getProperty(String key) {
		return (String) linkMap.get(key);
	}
}
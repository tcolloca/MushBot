package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

// TODO : Review
public class PrefixManager {

	private Map<String, String> prefixes;

	public PrefixManager() {
		prefixes = new HashMap<String, String>();
	}

	public void addString(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String> aux = new HashMap<String, String>();
		Iterator<Entry<String, String>> it = prefixes.entrySet().iterator();
		String longestMatch = "";
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			String match = getMatchingStart(s, value);
			// System.out.println("match between " + value + " and " + s + ": "
			// + match);
			if (match.length() >= key.length()) {
				// System.out.println("removing " + key + " - " + value);
				it.remove();
				add(aux, match, value);
			}
			longestMatch = longestMatch.length() >= match.length() ? longestMatch
					: match;
		}
		add(aux, longestMatch, s);
		prefixes.putAll(aux);
	}

	public void removeString(String s) {
		if (prefixes.containsKey(s)) {
			removeValue(s);
			Map<String, String> aux = new HashMap<String, String>(prefixes);
			prefixes = new HashMap<String, String>();
			for (Entry<String, String> e : aux.entrySet()) {
				prefixes.put(e.getKey(), e.getValue());
			}
		}
	}

	public String getString(String s) {
		String longestMatch = "";
		for (Entry<String, String> e : prefixes.entrySet()) {
			String prefix = e.getKey();
			if (s.startsWith(e.getKey())) {
				longestMatch = longestMatch.length() >= prefix.length() ? longestMatch
						: prefix;
			}
		}
		String value = prefixes.get(longestMatch);
		if (value.startsWith(s)) {
			return value;
		}
		return null;
	}

	private void add(Map<String, String> m, String match, String value) {
		if (match.equals(value)) {
			m.put(value, value);
			// System.out.println("Putting " + value + " - " + value);
		} else {
			m.put(value.substring(0, match.length() + 1), value);
			// System.out.println("Putting " + value.substring(0, match.length()
			// + 1) + " - " + value);
		}
	}

	private void removeValue(String value) {
		String key = null;
		for (Entry<String, String> e : prefixes.entrySet()) {
			if (e.getValue().equals(value)) {
				key = e.getKey();
			}
		}
		prefixes.remove(key);
	}

	private String getMatchingStart(String a, String b) {
		char[] arr1 = a.toCharArray();
		char[] arr2 = b.toCharArray();
		String ans = "";
		for (int i = 0; i < arr1.length && i < arr2.length; i++) {
			if (arr1[i] == arr2[i]) {
				ans += arr1[i];
			} else {
				break;
			}
		}
		return ans;
	}
}

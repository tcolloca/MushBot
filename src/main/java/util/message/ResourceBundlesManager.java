package util.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceBundlesManager {

	private static final String BUNDLE_NAME = "i18n.MessagesBundle";
	private static final String BUNDLE_PATH = BUNDLE_NAME.replace(".", "/");
	private static final String UNDERSCORE = "_";
	private static final String PROPERTIES = ".properties";

	private static Map<String, ResourceBundle> resourceBundles;

	static {
		loadResourcesBundle();
	}

	public static boolean hasLanguage(String lang) {
		return ClassLoader.getSystemResource(BUNDLE_PATH + UNDERSCORE + lang
				+ PROPERTIES) != null;
	}

	public static Set<String> getAvailableLanguages() {
		return resourceBundles.keySet();
	}

	static ResourceBundle getResourceBundle(String lang) {
		return resourceBundles.get(lang);
	}

	private static void loadResourcesBundle() {
		resourceBundles = new HashMap<String, ResourceBundle>();
		String[] langs = Locale.getISOLanguages();
		for (String lang : langs) {
			if (hasLanguage(lang)) {
				loadResourceBundle(lang);
			}
		}
	}

	private static void loadResourceBundle(String lang) {
		resourceBundles.put(lang, initResourceBundle(lang));
	}

	private static ResourceBundle initResourceBundle(String lang) {
		return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang));
	}
}

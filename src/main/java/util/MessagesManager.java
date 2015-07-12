package util;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class MessagesManager implements MessagesValues, DefaultValues {

	private String lang;
	private ResourceBundle resources;

	public MessagesManager() {
		Properties props = new Properties();
		InputStream input;
		try {
			input = ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTIES);
			props.load(input);
			changeLanguage(props.getProperty(DEFAULT_LANG));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		try {
			return resources.getString(key);
		} catch (MissingResourceException e) {
			System.out.print(key + " - ");
			return resources.getString(MESSAGE_MISSING);
		}
	}

	public String get(String key, Object[] args) {
		try {
			return MessageFormat.format(resources.getString(key), args);
		} catch (MissingResourceException e) {
			System.out.print(key + " - ");
			return resources.getString(MESSAGE_MISSING);
		}
	}

	public boolean hasLanguage(String lang) {
		return ClassLoader.getSystemResource(BUNDLE_PATH + UNDERSCORE + lang
				+ PROPERTIES) != null;
	}

	public void changeLanguage(String lang) {
		this.lang = lang;
		resources = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang));
	}

	public List<String> getAvailableLanguages() {
		String[] langs = Locale.getISOLanguages();
		List<String> availableLanguages = new ArrayList<String>(); 
		for (String lang : langs) {
			if (hasLanguage(lang)) {
				availableLanguages.add(lang);
			}
		}
		return availableLanguages;
	}

	public String getCurrentLanguage() {
		return lang;
	}
}

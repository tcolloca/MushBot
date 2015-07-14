package util.message;

import java.text.MessageFormat;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import properties.DefaultProperties;

public class MessagesManager {

	static final String MESSAGE_MISSING = "message_missing";
	private static final String DASH = " - ";

	private ResourceBundle resourceBundle;
	private String lang;

	public MessagesManager() {
		this(DefaultProperties.DEFAULT_LANG);
	}

	public MessagesManager(String lang) {
		if (ResourceBundlesManager.hasLanguage(lang)) {
			resourceBundle = ResourceBundlesManager.getResourceBundle(lang);
			this.lang = lang;
		} else {
			resourceBundle = ResourceBundlesManager
					.getResourceBundle(DefaultProperties.language());
			this.lang = DefaultProperties.language();
		}

	}

	public String get(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			System.out.print(key + DASH);
			return resourceBundle.getString(MESSAGE_MISSING);
		}
	}

	public String get(String key, List<String> args) {
		return get(key, args != null ? args.toArray() : null);
	}

	public String get(String key, Object[] args) {
		try {
			return MessageFormat.format(resourceBundle.getString(key), args);
		} catch (MissingResourceException e) {
			System.out.print(key + DASH);
			return resourceBundle.getString(MESSAGE_MISSING);
		}
	}

	public void changeLanguage(String lang) {
		resourceBundle = ResourceBundlesManager.getResourceBundle(lang);
		this.lang = lang;
	}

	public boolean hasLanguage(String lang) {
		return ResourceBundlesManager.hasLanguage(lang);
	}

	public Set<String> getAvailableLanguages() {
		return ResourceBundlesManager.getAvailableLanguages();
	}

	public String getLanguage() {
		return lang;
	}
}

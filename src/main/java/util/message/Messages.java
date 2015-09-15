package util.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Gets the message associated with a given {@link Message} or a key and its
 * arguments, and a language and formats it using its arguments.
 * 
 * @author Tomas
 */
public class Messages {

	private static final String MESSAGE_MISSING = "message_missing";
	private static final String BUNDLES_PATH = "i18n";
	private static final String DASH = " - ";
	private static final String SLASH = "/";
	private static final String DOT = ".";

	private static Map<String, List<ResourceBundle>> resourceBundles;

	static {
		loadResourcesBundle();
	}

	/**
	 * Returns the message formated with the message's arguments in the given
	 * language. If there is no message associated with the message's key, then
	 * a missing message will be returned.
	 * 
	 * @param lang
	 * @param message
	 * @return the message in the given language formated with the message's
	 *         arguments or a missing message if there is no message associated
	 *         with the message's key.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null or {@code lang} is not
	 *             available.
	 */
	public static String getMessage(String lang, Message message) {
		return getMessage(lang, message.getKey(), message.getArgs());
	}

	/**
	 * Returns the message associated with the key in the given language. If
	 * there is no message associated with the key, then a missing message will
	 * be returned.
	 * 
	 * @param lang
	 * @param key
	 * @return the message associated with the key in the given language or a
	 *         missing message if there is no message associated with the key.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null or {@code lang} is not
	 *             available.
	 */
	public static String getMessage(String lang, String key) {
		if (lang == null || key == null || !hasLanguage(lang)) {
			throw new IllegalArgumentException();
		}
		List<ResourceBundle> resourceBundleList = getResourceBundleList(lang);
		for (ResourceBundle resourceBundle : resourceBundleList) {
			try {
				return resourceBundle.getString(key);
			} catch (MissingResourceException e) {
			}
		}
		System.out.print("Missing message: " + key + DASH);
		for (ResourceBundle resourceBundle : resourceBundleList) {
			try {
				return resourceBundle.getString(MESSAGE_MISSING);
			} catch (MissingResourceException e) {
			}
		}
		return MESSAGE_MISSING;
	}

	/**
	 * Returns the message associated with the key in the given language
	 * formated with args. If there is no message associated with the key, then
	 * a missing message will be returned.
	 * 
	 * @param lang
	 * @param key
	 * @param args
	 * @return the message associated with the key in the given language
	 *         formated with {@code args} or a missing message if there is no
	 *         message associated with the key.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null or {@code lang} is not
	 *             available.
	 */
	public static String getMessage(String lang, String key, String... args) {
		return getMessage(lang, key, Arrays.asList(args));
	}

	/**
	 * Returns the message associated with the key in the given language
	 * formated with args. If there is no message associated with the key, then
	 * a missing message will be returned.
	 * 
	 * @param lang
	 * @param key
	 * @param args
	 * @return the message associated with the key in the given language
	 *         formated with {@code args} or a missing message if there is no
	 *         message associated with the key.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null or {@code lang} is not
	 *             available.
	 */
	public static String getMessage(String lang, String key, List<String> args) {
		return getMessage(lang, key, args != null ? args.toArray() : null);
	}

	/**
	 * Returns the message associated with the key in the given language
	 * formated with args converted into strings. If there is no message
	 * associated with the key, then a missing message will be returned.
	 * 
	 * @param lang
	 * @param key
	 * @param args
	 * @return the message associated with the key in the given language
	 *         formated with {@code args} converted into string or a missing
	 *         message if there is no message associated with the key.
	 * @throws IllegalArgumentException
	 *             If any of the parameters is null or {@code lang} is not
	 *             available.
	 */
	public static String getMessage(String lang, String key, Object... args) {
		if (lang == null || key == null || !hasLanguage(lang)) {
			throw new IllegalArgumentException();
		}
		List<ResourceBundle> resourceBundleList = getResourceBundleList(lang);
		for (ResourceBundle resourceBundle : resourceBundleList) {
			try {
				return MessageFormat
						.format(resourceBundle.getString(key), args);
			} catch (MissingResourceException e) {
			}
		}
		System.out.print("Missing message: " + key + DASH);
		for (ResourceBundle resourceBundle : resourceBundleList) {
			try {
				return resourceBundle.getString(MESSAGE_MISSING);
			} catch (MissingResourceException e) {
			}
		}
		return MESSAGE_MISSING;
	}

	/**
	 * Returns {@code true} if the language is available.
	 * 
	 * @param language
	 * @return {@code true} if the language is available.
	 * @throws IllegalArgumentException
	 *             If {@code language} is null.
	 */
	public static boolean hasLanguage(String language) {
		if (language == null) {
			throw new IllegalArgumentException();
		}
		return ClassLoader.getSystemResource(BUNDLES_PATH + SLASH + language) != null;
	}

	/**
	 * Returns all the available languages.
	 * 
	 * @return all the available languages.
	 */
	public static Set<String> getAvailableLanguages() {
		return resourceBundles.keySet();
	}

	private static void loadResourcesBundle() {
		resourceBundles = new HashMap<String, List<ResourceBundle>>();
		String[] langs = Locale.getISOLanguages();
		for (String lang : langs) {
			if (hasLanguage(lang)) {
				resourceBundles.put(lang, new ArrayList<ResourceBundle>());
				loadResourceBundle(lang);
			}
		}
	}

	private static void loadResourceBundle(String lang) {
		for (ResourceBundles bundle : ResourceBundles.values()) {
			resourceBundles.get(lang).add(
					initResourceBundle(
							BUNDLES_PATH + DOT + lang + DOT
									+ bundle.getBundleName(), lang));
		}
	}

	private static ResourceBundle initResourceBundle(String bundleName,
			String lang) {
		return ResourceBundle.getBundle(bundleName, new Locale(lang));
	}

	private static List<ResourceBundle> getResourceBundleList(String lang) {
		return resourceBundles.get(lang);
	}
}

package i18n;

import java.util.Set;

public interface Internationalizable {

	/**
	 * Returns {@code true} if the language is available.
	 * 
	 * @param language
	 * @return {@code true} if the language is available.
	 * @throws IllegalArgumentException
	 *             If {@code language} is null.
	 */
	public boolean hasLanguage(String language);

	/**
	 * Returns the current language representation.
	 * 
	 * @return the current language representation.
	 */
	public String getLanguage();

	/**
	 * Sets the new language to the one received.
	 * 
	 * @param language
	 * @throws IllegalArgumentException
	 *             If {@code language} is null or it is not available.
	 */
	public void setLanguage(String language);

	/**
	 * Returns all the available languages.
	 * 
	 * @return all the available languages.
	 */
	public Set<String> getAvailableLanguages();
}

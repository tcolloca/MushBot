package properties;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class loads all the default properties of the Mush Bot from the default
 * properties file, and provides all of them.
 * 
 * @author Tomas
 */
public class DefaultProperties implements DefaultValues {

	private static Properties PROPERTIES = new Properties();

	static {
		InputStream input;
		try {
			input = ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTIES);
			PROPERTIES.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*** Language ***/

	/**
	 * Returns the default language of the mush bot.
	 * 
	 * @return the default language of the mush bot.
	 */
	public static String language() {
		return PROPERTIES.getProperty(DEFAULT_LANG);
	}

	/*** Timeouts ***/

	/**
	 * Returns the default joining phase timeout.
	 * 
	 * @return the default joining phase timeout.
	 */
	public static int joiningPhaseTimeout() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_TIMEOUT_JOINING_PHASE));
	}

	/**
	 * Returns the default mush attack phase timeout.
	 * 
	 * @return the default mush attack phase timeout.
	 */
	public static int mushAttackPhaseTimeout() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_TIMEOUT_MUSH_ATTACK_PHASE));
	}

	/*** Game Properties ***/

	/**
	 * Returns the default aproximate mush proportion.
	 * 
	 * @return the default aproximate mush proportion.
	 */
	public static double mushProportion() {
		return Double.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_MUSH_PROPORTION));
	}

	/**
	 * Returns the default minimum mush amount.
	 * 
	 * @return the default minimum mush amount.
	 */
	public static int minMushAmount() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_MIN_MUSH_AMOUNT));
	}

	/**
	 * Returns {@code true} if revoting is allowed as default.
	 * 
	 * @return {@code true} if revoting is allowed as default.
	 */
	public static boolean revotingAllowed() {
		return Boolean.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_REVOTING_ALLOWED));
	}

	/**
	 * Returns the maximum of number of tries that the bot will try to start a
	 * created Game when the timeout runs out.
	 * 
	 * @return the maximum of number of tries that the bot will try to start a
	 *         created Game when the timeout runs out.
	 */
	public static int startMaxTryNumber() {
		return Integer.valueOf(PROPERTIES.getProperty(DEFAULT_MAX_TRY_NUMBER));
	}

	/**
	 * Returns the name of the default role.
	 * 
	 * @return the name of the default role.
	 */
	public static String roleName() {
		return PROPERTIES.getProperty(DEFAULT_ROLE_NAME);
	}
}

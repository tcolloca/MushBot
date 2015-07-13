package properties;

import java.io.InputStream;
import java.util.Properties;

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

	public static String language() {
		return PROPERTIES.getProperty(DEFAULT_LANG);
	}

	/*** Timeouts ***/

	public static int joiningPhaseTimeout() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_TIMEOUT_JOINING_PHASE));
	}

	public static int mushAttackPhaseTimeout() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_TIMEOUT_MUSH_ATTACK_PHASE));
	}

	/*** Game Properties ***/

	public static double mushProportion() {
		return Double.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_MUSH_PROPORTION));
	}

	public static int minMushAmount() {
		return Integer.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_MIN_MUSH_AMOUNT));
	}

	public static boolean revotingAllowed() {
		return Boolean.valueOf(PROPERTIES
				.getProperty(DEFAULT_GAME_REVOTING_ALLOWED));
	}
}

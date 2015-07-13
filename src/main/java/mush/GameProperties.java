package mush;

import java.io.InputStream;
import java.util.Properties;

public class GameProperties implements GameValues {

	private static Properties PROPERTIES = new Properties();

	static {
		InputStream input;
		try {
			input = ClassLoader.getSystemResourceAsStream(GAME_PROPERTIES);
			PROPERTIES.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double mushProportion() {
		return Double.valueOf(PROPERTIES.getProperty(GAME_MUSH_PROPORTION));
	}

	public static int minMushAmount() {
		return Integer.valueOf(PROPERTIES.getProperty(GAME_MIN_MUSH_AMOUNT));
	}

	public static boolean isRevotingAllowed() {
		return Boolean.valueOf(PROPERTIES.getProperty(GAME_REVOTING_ALLOWED));
	}

	public static String mushChannelPrefix() {
		return PROPERTIES.getProperty(GAME_MUSH_CHANNEL_PREFIX);
	}
}

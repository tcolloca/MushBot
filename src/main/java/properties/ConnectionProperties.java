package properties;

import java.io.InputStream;
import java.util.Properties;

import bot.ConnectionValues;

public class ConnectionProperties implements ConnectionValues {

	private static Properties PROPERTIES = new Properties();

	static {
		InputStream input;
		try {
			input = ClassLoader
					.getSystemResourceAsStream(CONNECTION_PROPERTIES);
			PROPERTIES.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String nickname() {
		return PROPERTIES.getProperty(IRC_NICKNAME);
	}
	
	public static String password() {
		return PROPERTIES.getProperty(IRC_PASSWORD);
	}
	
	public static String server() {
		return PROPERTIES.getProperty(IRC_SERVER);
	}
	
	public static String channel() {
		return PROPERTIES.getProperty(IRC_CHANNEL);
	}
	
	public static String encoding() {
		return PROPERTIES.getProperty(IRC_ENCODING);
	}
}

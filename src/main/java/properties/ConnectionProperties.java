package properties;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class loads all the properties of the connection properties file, and
 * provides all of them.
 * 
 * @author Tomas
 */
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

	/**
	 * Returns the nickname that will have the mush bot.
	 * 
	 * @return the nickname that will have the mush bot.
	 */
	public static String nickname() {
		return PROPERTIES.getProperty(IRC_NICKNAME);
	}

	/**
	 * Returns the password associated with the mush bot nickname.
	 * 
	 * @return the password associated with the mush bot nickname.
	 */
	public static String password() {
		return PROPERTIES.getProperty(IRC_PASSWORD);
	}

	/**
	 * Returns the server name to which the bot will connect.
	 * 
	 * @return the server name to which the bot will connect.
	 */
	public static String server() {
		return PROPERTIES.getProperty(IRC_SERVER);
	}

	/**
	 * Returns the channel to which the bot will connect.
	 * 
	 * @return the channel to which the bot will connect.
	 */
	public static String channel() {
		return PROPERTIES.getProperty(IRC_CHANNEL);
	}

	/**
	 * Returns the encoding of the connection.
	 *
	 * @return the encoding of the connection.
	 */
	public static String encoding() {
		return PROPERTIES.getProperty(IRC_ENCODING);
	}
}

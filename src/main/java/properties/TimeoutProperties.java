package properties;

import java.io.InputStream;
import java.util.Properties;

public class TimeoutProperties implements TimeoutValues {
	
	private static Properties PROPERTIES = new Properties();

	static {
		InputStream input;
		try {
			input = ClassLoader
					.getSystemResourceAsStream(TIMEOUT_PROPERTIES);
			PROPERTIES.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

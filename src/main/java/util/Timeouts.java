package util;

import java.io.InputStream;
import java.util.Properties;

public class Timeouts implements TimeoutValues {

	private Properties props;
	
	public Timeouts() {
		props = new Properties();
		InputStream input;
		try {
			input = ClassLoader
					.getSystemResourceAsStream(TIMEOUT_PROPERTIES);
			props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getJoiningPhaseTimeout() {
		return Integer.valueOf(props.getProperty(TIMEOUT_JOINING_PHASE));
	}
	
	public int getMushAttackPhaseTimeout() {
		return Integer.valueOf(props.getProperty(TIMEOUT_MUSH_ATTACK_PHASE));
	}
}

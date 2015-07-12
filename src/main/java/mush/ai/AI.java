package mush.ai;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import mush.player.Player;
import bot.MushBot;

public class AI implements GameValues {

	private Properties props;

	public AI(MushBot bot) {
		props = new Properties();
		InputStream input;
		try {
			input = ClassLoader.getSystemResourceAsStream(GAME_PROPERTIES);
			props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startGame(List<Player> players) {
		int mushAmount = calculateMush(players.size());
		Collections.shuffle(players);
		for (int i = 0; i < mushAmount; i++) {
			Player player = players.get(i);
			player.convertToMush();
		}
	}
	
	public boolean canStart(List<Player> players) {
		return players.size() >= getRequiredPlayers();
	}
	
	public int getRequiredPlayers() {
		return (int) Math.round(getMinMushAmount()/getMushProportion());
	}
	
	public double getMushProportion() {
		return Double.valueOf(props.getProperty(MUSH_PROPORTION));
	}
	
	public int getMinMushAmount() {
		return Integer.valueOf(props.getProperty(MIN_MUSH_AMOUNT));
	}

	private int calculateMush(int size) {
		return (int) Math.round(size * getMushProportion());
	}

	public boolean isAllowedRevoting() {
		return Boolean.valueOf(props.getProperty(REVOTING_ALLOWED));
	}
}

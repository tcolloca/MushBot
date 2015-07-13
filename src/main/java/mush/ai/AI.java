package mush.ai;

import java.util.Collections;
import java.util.List;

import mush.player.Player;
import mush.properties.GameProperties;

public class AI {

	GameProperties gameProperties;
	
	public AI(GameProperties gameProperties) {
		this.gameProperties = gameProperties;
	}

	public void startGame(List<Player> players) {
		int mushAmount = calculateMush(players.size());
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			if (i < mushAmount) {
				player.convertToMush();
			} else {
				player.convertToHuman();
			}
		}
	}

	public boolean canStart(List<Player> players) {
		return players.size() >= getRequiredPlayers();
	}

	public int getRequiredPlayers() {
		return (int) Math.round(gameProperties.getMinMushAmount()
				/ gameProperties.getMushProportion());
	}

	private int calculateMush(int size) {
		return (int) Math.round(size * gameProperties.getMushProportion());
	}
}

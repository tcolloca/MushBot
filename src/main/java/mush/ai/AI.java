package mush.ai;

import java.util.Collections;
import java.util.List;

import mush.GameProperties;
import mush.player.Player;

public class AI {

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
		return (int) Math.round(GameProperties.minMushAmount()
				/ GameProperties.mushProportion());
	}

	private int calculateMush(int size) {
		return (int) Math.round(size * GameProperties.mushProportion());
	}
}

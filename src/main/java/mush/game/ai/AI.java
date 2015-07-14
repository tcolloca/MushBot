package mush.game.ai;

import java.util.Collections;
import java.util.List;

import mush.game.Tripulation;
import mush.game.player.Player;
import mush.properties.GameProperties;

public class AI {

	GameProperties gameProperties;

	public AI(GameProperties gameProperties) {
		this.gameProperties = gameProperties;
	}

	public void startGame(Tripulation tripulation) {
		List<Player> players = tripulation.getPlayers();
		assignRoles(players);
		tripulation.build();
	}

	public boolean canStart(Tripulation tripulation) {
		return tripulation.size() >= getRequiredPlayers();
	}

	public int getRequiredPlayers() {
		return (int) Math.round(gameProperties.getMinMushAmount()
				/ gameProperties.getMushProportion());
	}

	private void assignRoles(List<Player> players) {
		Collections.shuffle(players);
		int mushAmount = calculateMush(players.size());
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			if (i < mushAmount) {
				player.convertToMush();
			} else {
				player.convertToHuman();
			}
		}
	}

	private int calculateMush(int size) {
		return (int) Math.round(size * gameProperties.getMushProportion());
	}
}

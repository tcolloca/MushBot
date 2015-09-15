package mush.game.action.player;

import java.util.Collections;
import java.util.List;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.validator.InGameStartValidator;
import mush.game.action.validator.StartValidator;
import mush.game.player.Player;
import mush.game.properties.GameProperties;

public class StartAction extends PlayerAction {

	private int playersAmount;
	private int mushAmount;

	public StartAction() {
		super(new InGameStartValidator());
	}
	
	public StartAction(GameUser performer) {
		super(performer, new StartValidator());
	}

	@Override
	void actionate(Game game) {
		game.getEventMulticaster().onStartGame();
		commit(game);
	}

	public void commit(Game mushGame) {
		playersAmount = mushGame.getPlayers().size();
		mushAmount = calculateMush(mushGame.getGameProperties(), playersAmount);
		mushGame.getEventMulticaster().onPerformAction(this);
		assignRoles(mushGame.getPlayers());
		mushGame.getStatus().next();
	}

	public int getPlayersAmount() {
		return playersAmount;
	}

	public int getMushAmount() {
		return mushAmount;
	}

	private void assignRoles(List<Player> players) {
		Collections.shuffle(players);
		for (int i = 0; i < playersAmount; i++) {
			Player player = players.get(i);
			if (i < mushAmount) {

			} else {
				player.convertToHuman();
			}
		}
	}

	private int calculateMush(GameProperties gameProperties, int size) {
		return (int) Math.round(size * gameProperties.getMushProportion());
	}
}

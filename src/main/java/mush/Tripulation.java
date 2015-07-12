package mush;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mush.player.Player;

import org.pircbotx.User;

import util.CollectionFilter;
import util.Filter;

public class Tripulation {

	private List<Player> players;
	private int playersAmount;
	private int mushAmount;
	private List<User> mushUsers;

	Tripulation() {
		players = new ArrayList<Player>();
		mushUsers = new ArrayList<User>();
	}

	public void build() {
		playersAmount = players.size();
		for (Player player : players) {
			if (player.isMush()) {
				mushAmount++;
				mushUsers.add(player.getUser());
			}
		}
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void killPlayer(Player player) {
		players.remove(player);
		if (player.isMush()) {
			mushUsers.remove(player.getUser());
		}
	}

	public int getPlayersAmount() {
		return playersAmount;
	}

	public int getMushAmount() {
		return mushAmount;
	}

	public List<User> getMushUsers() {
		return mushUsers;
	}

	public List<Player> getHumans() {
		return CollectionFilter.filter(players, new Filter<Player>() {
			public boolean evaluate(Player player) {
				return !player.isMush();
			}
		});
	}

	public List<Player> getMush() {
		return CollectionFilter.filter(players, new Filter<Player>() {
			public boolean evaluate(Player player) {
				return player.isMush();
			}
		});
	}

	public Player getRandomMush() {
		List<Player> mush = getMush();
		Collections.shuffle(mush);
		return mush.get(0);
	}

	public List<Player> getPlayers() {
		return players;
	}
}

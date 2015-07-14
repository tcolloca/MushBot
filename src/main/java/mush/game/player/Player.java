package mush.game.player;

import java.util.List;

import mush.game.player.role.HumanRole;
import mush.game.player.role.MushRole;
import mush.game.player.role.Role;
import chat.User;

public class Player {

	private User user;
	private Role role;

	public Player(User user) {
		super();
		this.user = user;
	}

	public void convertToMush() {
		role = new MushRole();
	}

	public void convertToHuman() {
		role = new HumanRole();
	}

	public boolean isMush() {
		return role != null && role.isMush();
	}

	public User getUser() {
		return user;
	}

	public String getNick() {
		return user.getNick();
	}

	public List<String> getRoleNames() {
		return role.getRoleNames();
	}

	public int getAvailableVotes() {
		return role.getAvailableVotes();
	}

	public String toString() {
		return getNick();
	}

	public boolean isLeaderVoter() {
		return role.isLeaderVoter();
	}

	public boolean is(String roleName) {
		return role != null && role.is(roleName);
	}
}

package mush.player;

import java.util.List;

import mush.role.HumanRole;
import mush.role.MushRole;
import mush.role.Role;

import org.pircbotx.User;

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
		return role.isMush();
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
}

package mush.player;

import mush.role.BasicRole;
import mush.role.MushRole;
import mush.role.Role;

import org.pircbotx.User;

public class Player {

	private User user;
	private Role role;

	public Player(User user) {
		super();
		this.user = user;
		this.role = new BasicRole();
	}

	public void convertToMush() {
		role = new MushRole(role);
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

	public int getAvailableVotes() {
		return role.getAvailableVotes();
	}
	
	public String toString() {
		return getNick();
	}
}

package mush.game.player;

import java.util.List;

import mush.game.player.role.HumanRole;
import mush.game.player.role.MushRole;
import mush.game.player.role.Role;
import chat.ChatUser;

public class Player {

	private ChatUser user;
	private Role role;

	public Player(ChatUser user) {
		super();
		this.user = user;
	}

	public Player() {
		// TODO Auto-generated constructor stub
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

	public ChatUser getUser() {
		return user;
	}

	public String getNick() {
		return user.getUsername();
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

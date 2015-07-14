package mush.game.player.role;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class BasicRole implements Role {

	public int getAvailableVotes() {
		return 1;
	}

	public boolean isLeaderVoter() {
		return false;
	}

	public List<String> getRoleNames() {
		return Lists.newArrayList();
	}

	public abstract boolean isMush();
}

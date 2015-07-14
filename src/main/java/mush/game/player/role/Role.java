package mush.game.player.role;

import java.util.List;

public interface Role extends RoleValues {

	public boolean isMush();

	public int getAvailableVotes();

	public boolean isLeaderVoter();
	
	public List<String> getRoleNames();

	public boolean is(String string);
}

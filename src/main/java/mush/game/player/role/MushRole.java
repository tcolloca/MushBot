package mush.game.player.role;

import java.util.List;

public class MushRole extends BasicRole implements Role {

	@Override
	public boolean isMush() {
		return true;
	}

	public List<String> getRoleNames() {
		List<String> roleNames = super.getRoleNames();
		roleNames.add(ROLE_MUSH);
		return roleNames;
	}
}

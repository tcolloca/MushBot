package mush.game.player.role;

import java.util.List;

public class HumanRole extends BasicRole implements Role {

	@Override
	public boolean isMush() {
		return false;
	}
	
	public List<String> getRoleNames() {
		List<String> roleNames = super.getRoleNames();
		roleNames.add(ROLE_HUMAN);
		return roleNames;
	}
	
}

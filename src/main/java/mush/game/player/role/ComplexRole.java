package mush.game.player.role;

public abstract class ComplexRole implements Role {

	protected Role role;
	
	ComplexRole(Role role) {
		this.role = role;
	}

	public boolean isMush() {
		return role.isMush();
	}
	
	public int getAvailableVotes() {
		return role.getAvailableVotes();
	}
	
	public boolean isLeaderVoter() {
		if (isMush()) {
			return false;
		}
		return role.isLeaderVoter();
	}
}

package mush.role;

public abstract class ComplexRole implements Role {

	protected Role role;
	
	ComplexRole(Role role) {
		this.role = role;
	}

	public abstract boolean isMush();
	
	public int getAvailableVotes() {
		return role.getAvailableVotes();
	}
}

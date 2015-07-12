package mush.role;

public class MushRole extends ComplexRole implements Role {

	public MushRole(Role role) {
		super(role);
	}

	@Override
	public boolean isMush() {
		return true;
	}
}

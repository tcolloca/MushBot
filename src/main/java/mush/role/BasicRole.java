package mush.role;

public class BasicRole implements Role {

	public boolean isMush() {
		return false;
	}

	public int getAvailableVotes() {
		return 1;
	}

}

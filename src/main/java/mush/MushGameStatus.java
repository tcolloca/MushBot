package mush;

public enum MushGameStatus {

	JOINING_PHASE, STARTING_PHASE, MUSH_ATTACK_PHASE, ENDED;
	
	public boolean hasStarted() {
		return this != ENDED;
	}
	
	public boolean isJoiningPhase() {
		return this == JOINING_PHASE;
	}
	
	public boolean isStartingPhase() {
		return this == STARTING_PHASE;
	}
	
	public boolean isMushAttackPhase() {
		return this == MUSH_ATTACK_PHASE;
	}
	
	public boolean isEnded() {
		return this == ENDED;
	}

	public boolean isVotingPhase() {
		return isMushAttackPhase();
	}
}

package mush.game;

/**
 * Represents the different possible phases of a Mush Game.
 * 
 * @author Tomas
 */
public enum Phase {

	CREATE("create_phase"), JOIN("join_phase"), PRE_MUSH_ATTACK(
			"pre_mush_attack_phase"), MUSH_ATTACK("mush_attack_phase"), PRE_VOTE(
			"pre_vote_phase"), VOTE("vote_phase"), END("end_phase");

	private String name;

	private Phase(String name) {
		this.name = name;
	}

	public boolean isCreatePhase() {
		return this == CREATE;
	}

	public boolean isJoinPhase() {
		return this == JOIN;
	}

	public boolean isPreMushAttackPhase() {
		return this == PRE_MUSH_ATTACK;
	}

	public boolean isMushAttackPhase() {
		return this == MUSH_ATTACK;
	}

	public boolean isPreVotePhase() {
		return this == PRE_VOTE;
	}

	public boolean isVotePhase() {
		return this == PRE_VOTE;
	}

	public boolean isEndPhase() {
		return this == END;
	}

	public boolean isVotingPhase() {
		return isMushAttackPhase() || isVotePhase();
	}

	public String getName() {
		return name;
	}
}

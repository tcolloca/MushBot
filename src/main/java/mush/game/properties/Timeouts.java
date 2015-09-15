package mush.game.properties;

import properties.DefaultProperties;

public class Timeouts {

	private int joiningPhaseTimeout;
	private int mushAttackPhaseTimeout;

	Timeouts() {
		setJoiningPhaseTimeout(DefaultProperties.joiningPhaseTimeout());
		setMushAttackPhaseTimeout(DefaultProperties.mushAttackPhaseTimeout());
	}

	int getJoiningPhaseTimeout() {
		return joiningPhaseTimeout;
	}

	int getMushAttackPhaseTimeout() {
		return mushAttackPhaseTimeout;
	}

	void setJoiningPhaseTimeout(int joiningPhaseTimeout) {
		this.joiningPhaseTimeout = joiningPhaseTimeout;
	}

	void setMushAttackPhaseTimeout(int mushAttackPhaseTimeout) {
		this.mushAttackPhaseTimeout = mushAttackPhaseTimeout;
	}
}

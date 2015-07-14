package mush.properties;

import properties.DefaultProperties;

public class GameProperties {

	private double mushProportion;
	private int minMushAmount;
	private boolean revotingAllowed;

	Timeouts timeouts;

	public GameProperties() {
		setMushProportion(DefaultProperties.mushProportion());
		setMinMushAmount(DefaultProperties.minMushAmount());
		setRevotingAllowed(DefaultProperties.revotingAllowed());
		timeouts = new Timeouts();
	}

	public double getMushProportion() {
		return mushProportion;
	}

	public int getMinMushAmount() {
		return minMushAmount;
	}

	public boolean isRevotingAllowed() {
		return revotingAllowed;
	}

	public void setMushProportion(double mushProportion) {
		this.mushProportion = mushProportion;
	}

	public void setMinMushAmount(int minMushAmount) {
		this.minMushAmount = minMushAmount;
	}

	public void setRevotingAllowed(boolean revotingAllowed) {
		this.revotingAllowed = revotingAllowed;
	}

	public int getJoiningPhaseTimeout() {
		return timeouts.getJoiningPhaseTimeout();
	}

	public int getMushAttackPhaseTimeout() {
		return timeouts.getMushAttackPhaseTimeout();
	}

	public void setJoiningPhaseTimeout(int joiningPhaseTimeout) {
		timeouts.setJoiningPhaseTimeout(joiningPhaseTimeout);
	}

	public void setMushAttackPhaseTimeout(int mushAttackPhaseTimeout) {
		timeouts.setMushAttackPhaseTimeout(mushAttackPhaseTimeout);
	}
}

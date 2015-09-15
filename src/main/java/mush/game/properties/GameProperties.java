package mush.game.properties;

import properties.DefaultProperties;

/**
 * Contains the different game properties values and allows to change them.
 * 
 * @author Tomas
 */
public class GameProperties {

	private double mushProportion;
	private int minMushAmount;
	private boolean revotingAllowed;
	private int startMaxTryNumber;

	Timeouts timeouts;

	public GameProperties() {
		setMushProportion(DefaultProperties.mushProportion());
		setMinMushAmount(DefaultProperties.minMushAmount());
		setRevotingAllowed(DefaultProperties.revotingAllowed());
		setStartMaxTryNumber(DefaultProperties.startMaxTryNumber());
		timeouts = new Timeouts();
	}

	/**
	 * Returns the default aproximate mush proportion.
	 * 
	 * @return the default aproximate mush proportion.
	 */
	public double getMushProportion() {
		return mushProportion;
	}

	/**
	 * Returns the default minimum mush amount.
	 * 
	 * @return the default minimum mush amount.
	 */
	public int getMinMushAmount() {
		return minMushAmount;
	}

	/**
	 * Returns {@code true} if revoting is allowed as default.
	 * 
	 * @return {@code true} if revoting is allowed as default.
	 */
	public boolean isRevotingAllowed() {
		return revotingAllowed;
	}

	/**
	 * Returns the maximum of number of tries that the bot will try to start a
	 * created Game when the timeout runs out.
	 * 
	 * @return the maximum of number of tries that the bot will try to start a
	 *         created Game when the timeout runs out.
	 */
	public int getStartMaxTryNumber() {
		return startMaxTryNumber;
	}

	/**
	 * Returns the default joining phase timeout.
	 * 
	 * @return the default joining phase timeout.
	 */
	public int getJoiningPhaseTimeout() {
		return timeouts.getJoiningPhaseTimeout();
	}

	/**
	 * Returns the default mush attack phase timeout.
	 * 
	 * @return the default mush attack phase timeout.
	 */
	public int getMushAttackPhaseTimeout() {
		return timeouts.getMushAttackPhaseTimeout();
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

	public void setStartMaxTryNumber(int startMaxTryNumber) {
		this.startMaxTryNumber = startMaxTryNumber;
	}

	public void setJoiningPhaseTimeout(int joiningPhaseTimeout) {
		timeouts.setJoiningPhaseTimeout(joiningPhaseTimeout);
	}

	public void setMushAttackPhaseTimeout(int mushAttackPhaseTimeout) {
		timeouts.setMushAttackPhaseTimeout(mushAttackPhaseTimeout);
	}
}

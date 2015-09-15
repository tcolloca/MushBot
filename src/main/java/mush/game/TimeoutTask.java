package mush.game;

import mush.game.action.player.StartAction;
import mush.game.action.player.StopAction;

// TODO!
/**
 * Class that runs and works as timeout for all the different time-limit phases
 * of the game.
 * 
 * @author Tomas
 */
public class TimeoutTask implements Runnable {

	private Game mushGame;

	private int startTryNumber;

	public TimeoutTask(Game mushGame) {
		this.mushGame = mushGame;
	}

	public void run() {
		while (!mushGame.getStatus().hasEnded()) {
			long initTime, currTime, timeLimit, spentTime, missingTime;
			boolean flag = true;
			switch (mushGame.getStatus().getPhase()) {
			case JOIN:
				initTime = System.currentTimeMillis();
				timeLimit = mushGame.getGameProperties()
						.getJoiningPhaseTimeout() * 1000;
				do {
					currTime = System.currentTimeMillis();
					spentTime = currTime - initTime;
					missingTime = timeLimit - spentTime;
					if (missingTime % 1000 == 0 && flag) {
						showMissingTime(missingTime);
						flag = false;
					} else {
						flag = true;
					}
				} while (missingTime > 0
						&& mushGame.getStatus().getPhase().isJoinPhase());
				if (mushGame.getStatus().getPhase().isJoinPhase()) {
					startTryNumber++;
					mushGame.performAction(new StartAction());
				}
				break;
			case MUSH_ATTACK:
				initTime = System.currentTimeMillis();
				timeLimit = mushGame.getGameProperties()
						.getMushAttackPhaseTimeout() * 1000;
				do {
					currTime = System.currentTimeMillis();
					spentTime = currTime - initTime;
					missingTime = timeLimit - spentTime;
					if (missingTime % 1000 == 0 && flag) {
						showMissingTime(missingTime);
						flag = false;
					} else {
						flag = true;
					}
				} while (missingTime > 0
						&& mushGame.getStatus().getPhase().isMushAttackPhase());
				if (mushGame.getStatus().getPhase().isMushAttackPhase()) {
					mushGame.performAction(new StopAction());
				}
				break;
			default:
				break;
			}
			if (mushGame.getStatus().hasEnded()) {
				break;
			}
		}
	}

	public int getStartTryNumber() {
		return startTryNumber;
	}

	private void showMissingTime(long missingTime) {
		mushGame.getEventMulticaster().onTimeoutNotification(
				mushGame.getStatus().getPhase(), missingTime);
	}
}

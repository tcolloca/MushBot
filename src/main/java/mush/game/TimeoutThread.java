package mush.game;

import mush.properties.GameProperties;

public class TimeoutThread implements Runnable {

	private MushGame mushGame;

	public TimeoutThread(MushGame mushGame) {
		this.mushGame = mushGame;
	}

	public void run() {
		GameProperties gameProperties = mushGame.getGameProperties();
		while (!mushGame.getStatus().isEnded()) {
			long initTime, currTime;
			switch (mushGame.getStatus()) {
			case JOINING_PHASE:
				initTime = System.currentTimeMillis();
				do {
					currTime = System.currentTimeMillis();
				} while (currTime - initTime < gameProperties
						.getJoiningPhaseTimeout() * 1000
						&& mushGame.getStatus().isJoiningPhase());
				if (mushGame.getStatus().isJoiningPhase()) {
					mushGame.startGame();
				}
				break;
			case MUSH_ATTACK_PHASE:
				initTime = System.currentTimeMillis();
				do {
					currTime = System.currentTimeMillis();
				} while (currTime - initTime < gameProperties
						.getMushAttackPhaseTimeout() * 1000
						&& mushGame.getStatus().isMushAttackPhase());
				if (mushGame.getStatus().isMushAttackPhase()) {
					mushGame.endMushAttack();
				}
				break;
			default:
				break;

			}
		}
	}
}

package team009.utils;

import battlecode.common.Clock;
import battlecode.common.GameConstants;

public class Timer {
	public static final void StartTimer() {
		time = -Clock.getBytecodeNum();
		round = -Clock.getRoundNum();
	}

	public static final void EndTimer() {
		round += Clock.getRoundNum();

		if (round == 0) {
			System.out.println("ByteCodes: " + (time + Clock.getBytecodeNum() - TIMER_COSTS));
		} else {
			System.out.println("ByteCodes: " + (time + (round * 10000) + Clock.getBytecodeNum() - TIMER_COSTS));
		}
	}

    public static final int GetRounds(int calcs) {
        return (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 50)) / calcs;
    }

    public static final int GetRounds(int calcs, int sub) {
        return (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + sub)) / calcs;
    }

	private static int time;
	private static int round;
	private static final int TIMER_COSTS = 20;
}
package team009;

import battlecode.common.RobotController;

public class RobotPlayer {
	public static void run(RobotController rc) {
		while (true) {
			try {
				// End turn
				rc.yield();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

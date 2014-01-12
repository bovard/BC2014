package dumbPastHunter;

import battlecode.common.RobotController;
import battlecode.common.RobotType;
import dumbPastHunter.robot.HQ;
import dumbPastHunter.robot.soldier.DumbPastrHunter;

public class RobotPlayer {
	public static void run(RobotController rc) {
        try {
            RobotInformation info = new RobotInformation(rc);

            while (true) {
                if (rc.getType() == RobotType.HQ) {
                    new HQ(rc, info).run();
                }

                if (rc.getType() == RobotType.SOLDIER) {
                    new DumbPastrHunter(rc, info).run();
                }
                rc.yield();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package team009;

import battlecode.common.RobotController;
import battlecode.common.RobotType;
import team009.robot.GenericSoldier;
import team009.robot.HQ;

public class RobotPlayer {
	public static void run(RobotController rc) {
        try {
            RobotInformation info = new RobotInformation(rc);

            while (true) {
                if (rc.getType() == RobotType.HQ) {
                    new HQ(rc, info).run();
                }

                if (rc.getType() == RobotType.SOLDIER) {
                    new GenericSoldier(rc, info).run();
                }
                rc.yield();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package team009;

import battlecode.common.RobotController;
import battlecode.common.RobotType;
import team009.robot.HQ;

public class RobotPlayer {
	public static void run(RobotController rc) {
        try {
            RobotInformation info = new RobotInformation(rc);

            while (true) {
                if (rc.getType() == RobotType.HQ) {
                    new HQ(rc, info).run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

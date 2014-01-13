package team009;

import battlecode.common.RobotController;
import battlecode.common.RobotType;
import team009.robot.NoiseTower;
import team009.robot.Pastr;
import team009.robot.SoundTower;
import team009.robot.soldier.SoldierSpawner;

public class RobotPlayer {
	public static void run(RobotController rc) {
        try {
            RobotInformation info = new RobotInformation(rc);

            while (true) {
                if (rc.getType() == RobotType.HQ) {
                    new HQ(rc, info).run();
                }

                if (rc.getType() == RobotType.SOLDIER) {
                    SoldierSpawner.getSoldier(rc, info).run();
                }

                if (rc.getType() == RobotType.NOISETOWER) {
                    new SoundTower(rc, info).run();
                }

                if (rc.getType() == RobotType.PASTR) {
                    new Pastr(rc, info).run();
                }
                if (rc.getType() == RobotType.NOISETOWER) {
                    new NoiseTower(rc, info).run();
                }
                rc.yield();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

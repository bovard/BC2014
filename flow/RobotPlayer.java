package flow;

import battlecode.common.RobotController;
import battlecode.common.RobotType;
import flow.robot.HQ;
import flow.robot.NoiseTower;
import flow.robot.Pastr;
import flow.robot.SoundTower;
import flow.robot.soldier.SoldierSpawner;

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

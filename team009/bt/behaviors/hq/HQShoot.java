package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import team009.bt.behaviors.Behavior;
import team009.robot.HQ;

public class HQShoot extends Behavior {
    private HQ hq;
    public HQShoot(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return hq.seesEnemy;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better Micro
        //DumbCombat.Attack(rc, hq.enemies, robot.currentLoc);
        System.out.println("Shooting");
        for (int i = 0; i < hq.enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(hq.enemies[i]);
            System.out.println(info.location.toString());

            // TODO: Actual micro!
            if (hq.rc.canAttackSquare(info.location)) {
                System.out.println("Shooting " + info.location.toString());
                hq.rc.attackSquare(info.location);
                break;
            }
        }
        System.out.println("Didn't shoot");

        return false;
    }
}

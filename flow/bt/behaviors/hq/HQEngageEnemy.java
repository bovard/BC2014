package flow.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import flow.bt.behaviors.Behavior;
import flow.robot.HQ;

public class HQEngageEnemy extends Behavior {
    private HQ hq;
    public HQEngageEnemy(HQ robot) {
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
        for (int i = 0; i < hq.enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(hq.enemies[i]);

            // TODO: Actual micro!
            if (hq.rc.canAttackSquare(info.location)) {
                hq.rc.attackSquare(info.location);
                break;
            }
        }
        return true;
    }
}

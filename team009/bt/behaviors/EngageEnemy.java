package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import team009.robot.soldier.BaseSoldier;

public class EngageEnemy extends Behavior {
    BaseSoldier gs;
    public EngageEnemy(BaseSoldier robot) {
        super(robot);
        gs = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return gs.seesEnemy;
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

        // Soldier selector takes care of "isActive" check.
        for (int i = 0; i < gs.enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(gs.enemies[i]);

            // TODO: Actual micro!
            if (rc.canAttackSquare(info.location)) {
                rc.attackSquare(info.location);
                break;
            } else {
                Direction dir = gs.currentLoc.directionTo(info.location);
                if (rc.canMove(dir)) {
                    rc.move(dir);
                    break;
                }
            }
        }

        return true;
    }
}

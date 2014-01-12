package flow.combat;
import battlecode.common.*;

public class DumbBomber {

    public static void Bomb(RobotController rc, Robot[] enemies, MapLocation currentLoc) throws GameActionException {

        // TeamRobot run takes care of "isActive" check.
        for (int i = 0; i < enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(enemies[i]);

            if (info.type == RobotType.HQ) {
                continue;
            }

            // TODO: Actual micro!
            if (rc.canAttackSquare(info.location)) {
                //rc.attackSquare(info.location);
                rc.selfDestruct();
                break;
            } else {
                Direction dir = currentLoc.directionTo(info.location);
                if (rc.canMove(dir)) {
                    rc.move(dir);
                    break;
                }
            }
        }
    }
}

package team009.toyBT.behaviors;

import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.RobotInformation;
import battlecode.common.GameActionException;
import team009.navigation.BugMove;
import team009.navigation.SnailMove;
import team009.robot.soldier.ToySoldier;

public class NearEnemyHQ extends ToyMoveToLocation {
    ToySoldier soldier;
    RobotInformation info;
    MapLocation nme;
    MapLocation hq;
    BugMove move;

    public NearEnemyHQ(ToySoldier soldier) {
        super(soldier);
        this.info = soldier.info;
        nme = info.enemyHq;
        hq = info.hq;
        move = new BugMove(soldier);
    }

    @Override
    public boolean pre() throws GameActionException {
        return nme.distanceSquaredTo(robot.currentLoc) < HQ_PROXIMITY;
    }

    public boolean run() throws GameActionException {
        if (robot.currentLoc.distanceSquaredTo(nme) <= HQ_TO_CLOSE) {
            if (move.destination == null || !move.destination.equals(hq)) {
                move.setDestination(hq);
            }
            move.move();
        }
        return true;
    }

    public static final int HQ_PROXIMITY = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 5, 2);
    public static final int HQ_TO_CLOSE = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 3, 2);
}


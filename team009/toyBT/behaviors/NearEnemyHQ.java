package team009.toyBT.behaviors;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.RobotInformation;
import battlecode.common.GameActionException;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class NearEnemyHQ extends ToyMoveToLocation {
    ToySoldier soldier;
    RobotInformation info;
    MapLocation nme;
    MapLocation hq;
    BugMove move;
    int currentDistance;
    MapLocation[] corners = new MapLocation[4];

    public NearEnemyHQ(ToySoldier soldier) {
        super(soldier);
        this.info = soldier.info;
        nme = info.enemyHq;
        hq = info.hq;
        move = new BugMove(soldier);

        corners[0] = new MapLocation(0, 0);
        corners[1] = new MapLocation(0, soldier.info.height);
        corners[2] = new MapLocation(soldier.info.width, soldier.info.height);
        corners[3] = new MapLocation(soldier.info.width, 0);
    }

    @Override
    public boolean pre() throws GameActionException {
        currentDistance = nme.distanceSquaredTo(robot.currentLoc);
        rc.setIndicatorString(2, "Proximity: " + currentDistance + " : TO_CLOSE" + HQ_TO_CLOSE + " :Required Proximity" + HQ_PROXIMITY + " Attack Range: " + RobotType.HQ.attackRadiusMaxSquared);
        return currentDistance < HQ_PROXIMITY;
    }

    public boolean run() throws GameActionException {
        if (currentDistance <= HQ_TO_CLOSE) {
            if (move.destination == null || !move.destination.equals(hq)) {
                move.setDestination(hq);
            }

            int i = 0;
            Direction dir = move.calcMove();

            for (; i < 5; i++) {
                if (dir != null) {
                    MapLocation newMap = robot.currentLoc.add(dir);
                    if (nme.distanceSquaredTo(newMap) > HQ_TO_CLOSE) {
                        move.move();
                    }
                }
                if (i < 4) {
                    move.setDestination(corners[i]);
                }
            }
        }
        return true;
    }

    public static final int HQ_PROXIMITY = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 2.23, 2);
    public static final int HQ_TO_CLOSE = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 1.23, 2);
}


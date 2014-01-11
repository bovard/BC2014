package team009.bt.behaviors;

import battlecode.common.*;
import team009.MapUtils;
import team009.robot.SoundTower;

public class SoundTowerBehavior extends Behavior {
    int currentDistance = 0;
    Direction currentDirection = null;
    SoundTower tower;

    public SoundTowerBehavior(SoundTower robot) {
        super(robot);
        tower = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
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
        // null
        if (currentDirection == null) {
            currentDirection = tower.getNextDirection();
            currentDistance = MAX_DISTANCE;
        }

        MapLocation loc = MapUtils.trim(travel(tower.currentLoc, currentDirection, currentDistance), robot.info);
        if (robot.currentLoc.distanceSquaredTo(loc) <= GameConstants.ATTACK_SCARE_RANGE + 10) {
            currentDirection = tower.getNextDirection();
            currentDistance = MAX_DISTANCE;
        }

        // attacks square.
        currentDistance--;
        rc.attackSquare(loc);

        return true;
    }

    // TODO: $BYTECODE$ Ez shortcuts could be made here
    private MapLocation travel(MapLocation loc, Direction dir, int distance) {
        for (int i = 0; i < distance; i++) {
            loc = loc.add(dir);
        }

        Direction dir2 = dir.opposite();
        while (loc.distanceSquaredTo(robot.currentLoc) > RobotType.NOISETOWER.attackRadiusMaxSquared) {
            loc = loc.add(dir2);
        }
        return loc;
    }

    private static final int MAX_DISTANCE = 18;
    private static final int MAX_DISTANCE_SQUARED = 400;
}


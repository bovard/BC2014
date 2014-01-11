package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
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
        if (currentDistance == 0) {
            currentDirection = tower.getNextDirection();
            currentDistance = MAX_DISTANCE;
        }

        // attacks square.
        rc.attackSquare(MapUtils.trim(travel(tower.currentLoc, currentDirection, currentDistance), robot.info));
        currentDistance--;

        return true;
    }

    // TODO: $BYTECODE$ Ez shortcuts could be made here
    private MapLocation travel(MapLocation loc, Direction dir, int distance) {
        for (int i = 0; i < distance; i++) {
            loc = loc.add(dir);
        }
        return loc;
    }
    private static final int MAX_DISTANCE = 25;
}


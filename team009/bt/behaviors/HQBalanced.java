package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.robot.HQ;

public class HQBalanced extends Behavior {
    private int last = 0;
    private HQ hq;
    public HQBalanced(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        // There are never any preconditions that mean we shouldn't enter this state
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // This state never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // There is no state here so nothing to reset!

    }

    @Override
    public boolean run() throws GameActionException {
        // Spawn a guy at a random location
        if (robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            int group;
            MapLocation pasture = new MapLocation(2, 2);
            if (last % 16 < 4) {
                pasture = new MapLocation(2, 2);
                group = 0;
            } else if (last % 16 < 8 ) {
                pasture = new MapLocation(robot.info.width - 2, 2);
                group = 1;
            } else if (last % 16 < 12) {
                pasture = new MapLocation(2, robot.info.height - 2);
                group = 2;
            } else {
                pasture = new MapLocation(robot.info.width - 2, robot.info.height - 2);
                group = 3;
            }
            last++;
            hq.createHerder(group, pasture);
        }
        return true;
    }
}

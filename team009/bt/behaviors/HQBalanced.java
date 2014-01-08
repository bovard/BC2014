package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.robot.HQ;

public class HQBalanced extends Behavior {
    private int pastureCount = 0;
    private int herderCount = 0;
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
        if (robot.rc.isActive()) {
            if (Math.random() > 0.75) {
                MapLocation pasture = new MapLocation(2, 2);
                if (pastureCount == 0) {
                    pasture = new MapLocation(2, 2);
                } else if (pastureCount == 1) {
                    pasture = new MapLocation(robot.info.width - 2, 2);
                } else if (pastureCount == 2) {
                    pasture = new MapLocation(2, robot.info.height - 2);
                } else if (pastureCount == 3) {
                    pasture = new MapLocation(robot.info.width - 2, robot.info.height - 2);
                    pastureCount = 0;
                }
                if (herderCount < pastureCount) {
                    hq.createHerder(0, pasture);
                    herderCount++;
                } else {
                    hq.createPastureCapturer(0, pasture);
                    pastureCount++;
                }
            } else {
                hq.createDumbSoldier(0);
            }
        }
        return true;
    }
}

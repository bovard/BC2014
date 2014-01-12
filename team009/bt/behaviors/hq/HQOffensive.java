package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import battlecode.common.GameConstants;
import team009.robot.HQ;
import team009.robot.TeamRobot;

public class HQOffensive extends Behavior {
    private HQ hq;

    public HQOffensive(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        // no preconditions
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        // spawn guys
        if (robot.rc.isActive() && robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            hq.createWolf(0);
        }
        // broadcast possible pasture locations?
        return true;
    }
}
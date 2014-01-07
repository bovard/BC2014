package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.robot.TeamRobot;

public class HQDefensive extends Behavior {
    public HQDefensive(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        // no preconditions
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Fill me in
        // make sure we always have a pasture in sight of the hq
        // spawn guards and ranchers
        return false;
    }
}

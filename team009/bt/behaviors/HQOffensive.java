package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.robot.HQ;
import team009.robot.TeamRobot;

public class HQOffensive extends Behavior {

    public HQOffensive(HQ robot) {
        super(robot);
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
        // TODO: Fill me in
        // spawn guys
        // broadcast possible pasture locations?
        return false;
    }
}

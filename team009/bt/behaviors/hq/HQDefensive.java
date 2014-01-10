package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.HQ;

public class HQDefensive extends Behavior {
    public HQDefensive(HQ robot) {
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

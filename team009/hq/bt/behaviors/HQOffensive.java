package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.hq.HQ;

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

        //robot count
        int robotCount = robot.rc.senseRobotCount();

        // spawn guys
        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            hq.createToySoldier(0);
            return true;
        }

        return true;
    }

}

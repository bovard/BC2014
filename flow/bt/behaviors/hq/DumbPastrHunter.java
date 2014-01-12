package flow.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import flow.bt.behaviors.Behavior;
import flow.robot.HQ;
import flow.robot.TeamRobot;

public class DumbPastrHunter extends Behavior {
    public DumbPastrHunter(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
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
        if (robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            ((HQ)robot).createDumbPastrHunter();
        }
        return true;
    }
}

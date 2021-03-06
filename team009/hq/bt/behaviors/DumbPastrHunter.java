package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.hq.HQ;

public class DumbPastrHunter extends Behavior {
    public DumbPastrHunter(TeamRobot robot) {
        super(robot);
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
        if (robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            ((HQ)robot).createDumbPastrHunter();
            return true;
        }
        return false;
    }
}

package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.robot.HQ;
import team009.robot.TeamRobot;

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
            return true;
        }
        return false;
    }
}

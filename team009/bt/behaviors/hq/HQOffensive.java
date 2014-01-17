package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

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
            hq.createToySoldier(TeamRobot.TOY_GROUP);
            return true;
        }

        return true;
    }

}

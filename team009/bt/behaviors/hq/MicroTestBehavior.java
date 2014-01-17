package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class MicroTestBehavior extends Behavior {
    private int backDoorCount = 0;
    private HQ hq;

    public MicroTestBehavior(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        int robotCount = robot.rc.senseRobotCount();

        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            hq.createToySoldier(TeamRobot.TOY_GROUP);
            return true;
        }

        return true;
    }
}


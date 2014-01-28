package team009.hq.bt.behaviors;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.hq.HQ;

public class MicroTestBehavior extends Behavior {
    private HQ hq;
    private int group = 0;
    private int lastGroupChange = 0;

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
            if (Clock.getRoundNum() - lastGroupChange > 250) {
                group = (group + 1) % Communicator.MAX_GROUP_COUNT;
                lastGroupChange = Clock.getRoundNum();
            }
            hq.createToySoldier(group);
            return true;
        }

        return true;
    }
}


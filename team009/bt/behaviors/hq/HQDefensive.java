package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class HQDefensive extends Behavior {
    HQ hq;
    public HQDefensive(HQ robot) {
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
        // never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        hq.createDefender(TeamRobot.DEFENDER_GROUP);
        return false;
    }
}

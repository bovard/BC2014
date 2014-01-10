package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
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
        return false;
    }
}

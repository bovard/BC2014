package team009.bt.behaviors.pasture;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;

public class Pasture extends Behavior {
    public Pasture(TeamRobot robot) {
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

        return true;
    }
}


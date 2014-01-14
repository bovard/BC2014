package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class BackDoorTester extends Behavior {
    private int backDoorCount = 0;
    public BackDoorTester(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if(robot.round/500 > backDoorCount - 1) {
            ((HQ)robot).createBackDoorNoisePlanter(0);
        }
        return true;
    }
}

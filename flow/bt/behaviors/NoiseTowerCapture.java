package flow.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import flow.robot.TeamRobot;

public class NoiseTowerCapture extends Behavior {
    public NoiseTowerCapture(TeamRobot robot) {
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
        rc.construct(RobotType.NOISETOWER);
        return true;
    }
}

package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.SoundTowerCapture;

public class SoundTowerCapturer extends Behavior {
    public SoundTowerCapturer(SoundTowerCapture robot) {
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
        //TODO: $REFACTOR$ Rename
        rc.construct(RobotType.NOISETOWER);
        return true;
    }
}


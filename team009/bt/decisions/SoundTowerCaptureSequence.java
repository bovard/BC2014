package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.SoundTowerCapturer;
import team009.robot.soldier.SoundTowerCapture;

public class SoundTowerCaptureSequence extends Sequence {

    public SoundTowerCaptureSequence(SoundTowerCapture robot) {
        super(robot);
        AggressiveMove move = new AggressiveMove(robot);
        move.location.setDestination(robot.captureLocation);

        children.add(move);
        children.add(new SoundTowerCapturer(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }
}


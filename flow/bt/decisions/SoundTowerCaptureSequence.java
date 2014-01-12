package flow.bt.decisions;

import battlecode.common.GameActionException;
import flow.bt.behaviors.SoundTowerCapturer;
import flow.robot.soldier.SoundTowerCapture;

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

    @Override
    public boolean run() throws GameActionException {
        return super.run();
    }
}


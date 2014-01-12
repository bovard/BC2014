package flow.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.decisions.SoundTowerCaptureSequence;

public class SoundTowerCapture extends BaseSoldier {
    public MapLocation captureLocation;
    public SoundTowerCapture(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        captureLocation = location;
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new SoundTowerCaptureSequence(this);
    }
}


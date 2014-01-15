package team009.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.soldier.SoundTowerCaptureSequence;

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


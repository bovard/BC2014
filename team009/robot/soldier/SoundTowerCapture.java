package team009.robot.soldier;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.SoundTowerCaptureSequence;

public class SoundTowerCapture extends BaseSoldier {
    public
    public SoundTowerCapture(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    public Direction getNextDirection() {
        currentDirection = ++currentDirection % directions.length;
        return directions[currentDirection];
    }

    @Override
    protected Node getTreeRoot() {
        return new SoundTowerCaptureSequence(this);
    }
}


package team009.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.soldier.PastureSelector;

public class PastureCapture extends BaseSoldier {
    private MapLocation pastrLocation;
    public PastureCapture(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        pastrLocation = location;
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new PastureSelector(this, pastrLocation);
    }
}

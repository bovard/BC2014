package flow.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.decisions.PastureSelector;

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

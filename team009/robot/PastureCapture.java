package team009.robot;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.DumbSoldierSelector;
import team009.bt.decisions.PastureSelector;

public class PastureCapture extends GenericSoldier {
    private MapLocation pastrLocation;
    public PastureCapture(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        pastrLocation = location;
    }

    @Override
    protected Node getTreeRoot() {
        return new PastureSelector(this, pastrLocation);
    }
}

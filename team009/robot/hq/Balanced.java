package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;

public class Balanced extends HQ {
    public Balanced(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return null;
    }
}

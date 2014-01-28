package team009.hq.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQ;
import team009.hq.bt.behaviors.BackDoorTester;

public class BackDoorTest extends HQ {
    public BackDoorTest(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new BackDoorTester(this);
    }
}

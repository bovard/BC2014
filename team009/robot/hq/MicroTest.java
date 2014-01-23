package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.hq.MicroTestBehavior;
import team009.bt.behaviors.hq.MicroTestComTree;

public class MicroTest extends HQ {
    public MicroTest(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new MicroTestComTree(this);
    }

    @Override
    protected Node getTreeRoot() {
        return new MicroTestBehavior(this);
    }
}


package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.hq.ComTestBehavior;
import team009.bt.behaviors.hq.ComTestComTree;

public class ComTest extends HQ {
    public ComTest(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new ComTestComTree(this);
    }

    @Override
    protected Node getTreeRoot() {
        return new ComTestBehavior(this);
    }
}


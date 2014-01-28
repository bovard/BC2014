package team009.hq.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQ;
import team009.hq.bt.behaviors.ComTestBehavior;
import team009.hq.bt.behaviors.ComTestComTree;

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


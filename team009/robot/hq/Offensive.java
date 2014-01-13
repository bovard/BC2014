package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.hq.OffensiveSelector;

public class Offensive extends HQ {
    public Offensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new OffensiveSelector(this);
    }
}

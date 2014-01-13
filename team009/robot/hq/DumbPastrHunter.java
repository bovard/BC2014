package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;

public class DumbPastrHunter extends HQ {
    public DumbPastrHunter(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new team009.bt.behaviors.hq.DumbPastrHunter(this);
    }
}

package team009.hq.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQ;

public class DumbPastrHunter extends HQ {
    public DumbPastrHunter(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new team009.hq.bt.behaviors.DumbPastrHunter(this);
    }
}

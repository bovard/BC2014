package team009.robot.soldier;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.DumbSoldierSelector;

public class DumbSoldier extends BaseSoldier {
    public DumbSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new DumbSoldierSelector(this);
    }
}

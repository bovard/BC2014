package flow.robot.soldier;

import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.decisions.DumbSoldierSelector;

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

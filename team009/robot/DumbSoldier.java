package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.DumbSoldierSelector;

public class DumbSoldier extends GenericSoldier {
    public DumbSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    protected Node getTreeRoot() {
        return new DumbSoldierSelector(this);
    }
}

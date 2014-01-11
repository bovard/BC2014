package team009.robot.soldier;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.JackalSelector;

public class Jackal extends BaseSoldier {

    public Jackal(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new JackalSelector(this);
    }
}

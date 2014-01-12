package flow.robot.soldier;

import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.decisions.JackalSelector;

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

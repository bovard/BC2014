package flow.robot;

import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.behaviors.NoiseTowerBehavior;

public class NoiseTower extends TeamRobot {
    public NoiseTower(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new NoiseTowerBehavior(this);
    }
}

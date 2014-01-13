package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.noise.NoiseSelector;

public class NoiseTower extends TeamRobot {
    public NoiseTower(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new NoiseSelector(this);
    }
}

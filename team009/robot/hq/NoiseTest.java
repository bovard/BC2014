package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.hq.NoiseTester;

/**
 * This should be used to test out which noise strat is the best!
 */
public class NoiseTest extends HQ {
    public NoiseTest(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new NoiseTester(this);
    }
}

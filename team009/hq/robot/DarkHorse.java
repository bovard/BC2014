package team009.hq.robot;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQ;
import team009.hq.bt.selectors.DarkHorseSelector;

public class DarkHorse extends HQ {
    public int robotCount = 0;

    public DarkHorse(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new DarkHorseSelector(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        robotCount = rc.senseRobotCount();
    }
}

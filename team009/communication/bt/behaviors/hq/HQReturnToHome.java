package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Offensive;

public class HQReturnToHome extends WriteBehavior {
    Offensive hq;

    public HQReturnToHome(Offensive off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        hq.comReturnHome(hq.bestCoverageLocation, 0);
        hq.comReturnHome(hq.bestCoverageLocation, 1);
        return true;
    }
}

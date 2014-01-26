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
        if (hq.rally.finished) {
            hq.comReturnHome(hq.rally.rallyPoint, 0);
        } else {
            hq.comReturnHome(hq.rally.center, 0);
        }
        return true;
    }
}

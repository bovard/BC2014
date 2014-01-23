package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Offensive;

public class HQOneBase extends WriteBehavior {
    Offensive hq;

    public HQOneBase(Offensive off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        hq.comCapture(hq.milkInformation.oneBaseBestSpot, 0, true);
        hq.comDefend(hq.milkInformation.oneBaseBestSpot, 1);
        return true;
    }
}

package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Offensive;

public class HQChase extends WriteBehavior {
    Offensive hq;

    public HQChase(Offensive off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.chase0) {
            hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
            if (!hq.chase1) {
                hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 1);
            }
        }
        if (hq.chase1) {
            hq.comCapture(hq.milkInformation.targetBoxes[1].bestSpot, 1);
            if (!hq.chase0) {
                hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
            }
        }
        return true;
    }
}


package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Seeding;

public class HQChase extends WriteBehavior {
    Seeding hq;

    public HQChase(Seeding off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.chase0 && hq.chase1) {
            MapLocation bestSpot0 = hq.getNextMilkingSpot();
            MapLocation bestSpot1 = hq.getNextMilkingSpot();

            hq.comCapture(bestSpot0, 0);
            hq.comCapture(bestSpot1, 1);
        } else if (hq.chase0 || hq.chase1) {
            MapLocation bestSpot = hq.getNextMilkingSpot();

            hq.comCapture(bestSpot, 0);
            hq.comCapture(bestSpot, 1);
        }
        return true;
    }
}


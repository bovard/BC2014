package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Seeding;

public class HQCheeseCom extends WriteBehavior {
    Seeding hq;

    // TODO: Keep track of what is actually placed so that way we can stop checking the rest of the pres.
    // make efficient!
    boolean soundTower = false;
    boolean pasture = false;
    boolean done = false;

    public HQCheeseCom(Seeding off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        if (done) {
            return true;
        }

        if (hq.getCount(2) > 0) {
            hq.comCapture(hq.cheeseStrat.pasture, 2, false);
        }

        if (hq.getCount(3) > 0) {
            hq.comSoundTower(hq.cheeseStrat.soundTower, 3);
        }
        return true;
    }
}


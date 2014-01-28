package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.communication.bt.behaviors.hq.*;
import team009.hq.robot.Qualifier;

public class HQOffensiveWriteCom extends WriteBehavior {
    Qualifier hq;

    HQOneBase oneBase;
    HQHunt hunt;
    HQReturnToHome home;
    HQSurround surround;

    public HQOffensiveWriteCom(Qualifier off) {
        super(off);
        hq = off;

        oneBase = new HQOneBase(off);
        hunt = new HQHunt(off);
        home = new HQReturnToHome(off);
        surround = new HQSurround(off);
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.hunt) {
            rc.setIndicatorString(1, "Running Hunt");
            hunt.run();
        } else if (hq.surround) {
            rc.setIndicatorString(1, "Running surround");
            surround.run();
        } else if (hq.oneBase || hq.soundTower) {
            rc.setIndicatorString(1, "Running OneBase");
            oneBase.run();
        } else if (hq.huddle) {
            rc.setIndicatorString(1, "Running Home: " + hq.rally.rallyPoint);
            home.run();
        }

        return true;
    }
}

package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.communication.bt.behaviors.hq.*;
import team009.robot.hq.Offensive;

public class HQOffensiveWriteCom extends WriteBehavior {
    Offensive hq;

    HQCheeseCom cheese;
    HQOneBase oneBase;
    HQChase chase;
    HQHunt hunt;
    HQReturnToHome home;

    public HQOffensiveWriteCom(Offensive off) {
        super(off);
        hq = off;

        oneBase = new HQOneBase(off);
        chase = new HQChase(off);
        hunt = new HQHunt(off);
        home = new HQReturnToHome(off);
        cheese = new HQCheeseCom(off);
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.cheese) {
            //rc.setIndicatorString(0, "Running Cheese");
            cheese.run();
        }

        if (hq.chase0 || hq.chase1) {
            //rc.setIndicatorString(1, "Running Chase");
            chase.run();
        } else if (hq.hunt0 || hq.hunt1) {
            //rc.setIndicatorString(1, "Running Hunt");
            hunt.run();
        } else if (hq.oneBase) {
            //rc.setIndicatorString(1, "Running OneBase");
            oneBase.run();
        } else if (hq.huddle) {
            //rc.setIndicatorString(1, "Running Home");
            home.run();
        }

        return true;
    }
}

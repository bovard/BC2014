package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.communication.bt.behaviors.hq.*;
import team009.hq.robot.Qualifier;

public class HQOffensiveWriteCom extends WriteBehavior {
    Qualifier hq;

    HQOneBase oneBase;

    public HQOffensiveWriteCom(Qualifier off) {
        super(off);
        hq = off;

        oneBase = new HQOneBase(off);
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.huntZero) {
            rc.setIndicatorString(1, "Group 0: Running Hunt");
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
        } else if (hq.surroundZero) {
            rc.setIndicatorString(1, "Group 0: Running surround");
            hq.comDefend(robot.info.enemyHq, 0);
        } else if (hq.baseZero) {
            rc.setIndicatorString(1, "Group 0: Running base");
            oneBase.run();
        } else if (hq.huddleZero) {
            rc.setIndicatorString(1, "Group 0: Running Home: " + hq.rally.rallyPoint);
            if (hq.rally.finished) {
                hq.comReturnHome(hq.rally.rallyPoint, 0);
            } else {
                hq.comReturnHome(hq.rally.center, 0);
            }
        }

        if (hq.huntComOne) {
            rc.setIndicatorString(2, "Group 1: Running Hunt Com");
            //TODO: This
            if (hq.enemyPastrs.arr.length > 1) {
                hq.comAttackPasture(hq.enemyPastrs.arr[1], 1);
            }
        } else if (hq.baseOne) {
            rc.setIndicatorString(2, "Group 1: Running base");
            oneBase.run();
        } else if (hq.huddleOne) {
            rc.setIndicatorString(2, "Group 1: Running Home: " + hq.rally.rallyPoint);
            if (hq.rally.finished) {
                hq.comReturnHome(hq.rally.rallyPoint, 1);
            } else {
                hq.comReturnHome(hq.rally.center, 1);
            }
        }



        return true;
    }
}

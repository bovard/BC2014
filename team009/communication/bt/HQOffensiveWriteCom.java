package team009.communication.bt;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.communication.bt.behaviors.hq.*;
import team009.hq.robot.Qualifier;

public class HQOffensiveWriteCom extends WriteBehavior {
    Qualifier hq;

    HQOneBase groupZeroBase;
    HQOneBase groupOneBase;

    public HQOffensiveWriteCom(Qualifier off) {
        super(off);
        hq = off;

        groupZeroBase = new HQOneBase(off, 0);
        groupOneBase = new HQOneBase(off, 1);

    }

    @Override
    public boolean run() throws GameActionException {
        rc.setIndicatorString(1, "");
        rc.setIndicatorString(2, "");
        if (hq.huntZero) {
            rc.setIndicatorString(1, "Group 0: Running Hunt Pastr: " + hq.enemyPastrs.arr[0] + " round: " + Clock.getRoundNum());
            // TODO: change this to a min heap based on distance to our hq // or negative disance to enemy hq
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
        } else if (hq.surroundZero) {
            rc.setIndicatorString(1, "Group 0: Running surround round: " + Clock.getRoundNum());
            hq.comDefend(robot.info.enemyHq, 0);
        } else if (hq.baseZero) {
            rc.setIndicatorString(1, "Group 0: Running base round: " + Clock.getRoundNum());
            groupZeroBase.run();
        } else if (hq.huddleZero) {
            if (hq.rally.finished) {
                rc.setIndicatorString(1, "Group 0: Running Huddle: " + hq.rally.rallyPoint + " round: " + Clock.getRoundNum());
                hq.comReturnHome(hq.rally.rallyPoint, 0);
            } else {
                rc.setIndicatorString(1, "Group 0: Running Huddle: " + hq.rally.center + " round: " + Clock.getRoundNum());
                hq.comReturnHome(hq.rally.center, 0);
            }
        } else {
            rc.setIndicatorString(2, "Group 0: Running Nothing! round: " + Clock.getRoundNum());
        }

        if (hq.huntPastrOne) {
            rc.setIndicatorString(2, "Group 1: Running Hunt: " + hq.enemyPastrs.arr[0] + " round: " + Clock.getRoundNum());
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 1);
            // TODO: change this to a min heap based on distance to our hq // or negative disance to enemy hq
        } else if (hq.baseOne) {
            rc.setIndicatorString(2, "Group 1: Running base round: " + Clock.getRoundNum());
            groupOneBase.run();
        } else if (hq.huddleOne) {
            if (hq.rally.finished) {
                rc.setIndicatorString(2, "Group 1: Running Huddle: " + hq.rally.rallyPoint + " round: " + Clock.getRoundNum());
                hq.comReturnHome(hq.rally.rallyPoint, 1);
            } else {
                rc.setIndicatorString(2, "Group 1: Running Huddle: " + hq.rally.center + " round: " + Clock.getRoundNum());
                hq.comReturnHome(hq.rally.center, 1);
            }
        } else if (hq.huntComOne) {
            rc.setIndicatorString(2, "Group 1: Running Hunt Com round: " + Clock.getRoundNum());
            hq.comDefend(robot.info.enemyHq, 0);
        } else {
            rc.setIndicatorString(2, "Group 1: Running Nothing! round: " + Clock.getRoundNum());
        }



        return true;
    }
}

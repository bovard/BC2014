package team009.communication.bt;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
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
            // get the pastr that is furthest from the enemy hq
            int pastr = hq.sortedPastrs.pop();
            MapLocation pastrLoc = new MapLocation(pastr / hq.info.width, pastr % hq.info.width);
            rc.setIndicatorString(1, "Group 0: Running Hunt Pastr: " + pastrLoc + " round: " + Clock.getRoundNum());
            hq.comAttackPasture(pastrLoc, 0);
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
            // get the pastr that is furthest from the enemy hq
            int pastr = hq.sortedPastrs.pop();
            MapLocation pastrLoc = new MapLocation(pastr / hq.info.width, pastr % hq.info.width);
            rc.setIndicatorString(2, "Group 1: Running Hunt: " + pastrLoc + " round: " + Clock.getRoundNum());
            hq.comAttackPasture(pastrLoc, 1);
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

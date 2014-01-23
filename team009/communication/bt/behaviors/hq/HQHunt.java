package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Seeding;

public class HQHunt extends WriteBehavior {
    Seeding hq;

    public HQHunt(Seeding off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        if (hq.hunt0 && hq.hunt1) {
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
            hq.comAttackPasture(hq.enemyPastrs.arr[1], 1);
        } else {
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 1);
        }
        return true;
    }
}


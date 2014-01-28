package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.hq.robot.Qualifier;

public class HQHunt extends WriteBehavior {
    Qualifier hq;

    public HQHunt(Qualifier off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
        return true;
    }
}


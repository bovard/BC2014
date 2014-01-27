package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Qualifier;

public class HQSurround extends WriteBehavior {
    Qualifier hq;

    public HQSurround(Qualifier off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        hq.comDefend(robot.info.enemyHq, 0);
        return true;
    }
}


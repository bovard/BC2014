package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.hq.HQ;

public class HQCheese extends Behavior {
    private HQ hq;
    private int spawned = 0;

    public HQCheese(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return spawned < 2;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        if (spawned == 0) {
            hq.createToySoldier(2);
            spawned++;
            return true;
        }
        else if (spawned == 1) {
            hq.createToySoldier(3);
            spawned++;
            return true;
        }
        return false;
    }
}


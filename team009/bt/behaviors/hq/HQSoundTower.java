package team009.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.HQ;

public class HQSoundTower extends Behavior {
    private HQ hq;
    private int spawned = 0;

    public HQSoundTower(HQ robot) {
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
            hq.createSoundTower(0, hq.info.hq.add(Direction.SOUTH));
        }
        if (spawned == 1) {
            hq.createHerder(0, hq.info.hq.add(Direction.NORTH));
        }
        spawned++;
        return false;
    }
}


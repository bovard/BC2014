package team009.hq.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.hq.HQ;

public class HQSoundTower extends Behavior {
    private HQ hq;
    private int spawned = 0;
    private MapLocation pasture;
    private MapLocation soundTower;

    public HQSoundTower(HQ robot) {
        super(robot);
        hq = robot;

        Direction perp = robot.info.enemyDir.rotateLeft().rotateLeft();
        pasture = robot.info.hq.add(perp);
        soundTower = robot.info.hq.add(perp.opposite());
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
            hq.createSoundTower(0, soundTower);
            spawned++;
            return true;
        }
        else if (spawned == 1) {
            hq.createHerder(0, pasture);
            spawned++;
            return true;
        }
        return false;
    }
}


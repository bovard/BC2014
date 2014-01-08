package team009.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.HerdReturn;
import team009.bt.behaviors.HerdSneak;
import team009.robot.soldier.Herder;

public class HerdSequence extends Sequence {
    protected MapLocation pastureLocation;

    public HerdSequence(Herder robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        children.add(new HerdSneak(robot, pastureLocation));
        children.add(new HerdReturn(robot, pastureLocation));
        lastRun = 0;
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        lastRun = 0;
    }
}

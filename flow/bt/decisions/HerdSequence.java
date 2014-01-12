package flow.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import flow.bt.behaviors.HerdReturn;
import flow.bt.behaviors.HerdSneak;
import flow.robot.soldier.Herder;

public class HerdSequence extends Sequence {
    protected MapLocation pastureLocation;

    public HerdSequence(Herder robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        children.add(new HerdReturn(robot, pastureLocation));
        children.add(new HerdSneak(robot, pastureLocation));
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

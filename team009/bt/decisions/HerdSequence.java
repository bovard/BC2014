package team009.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.Node;
import team009.bt.behaviors.HerdReturn;
import team009.bt.behaviors.HerdSneak;
import team009.robot.TeamRobot;

public class HerdSequence extends Sequence {
    protected MapLocation pastureLocation;
    protected Node hSneak;
    protected Node hReturn;
    public HerdSequence(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        children.add(new HerdSneak(robot, pastureLocation));
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

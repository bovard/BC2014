package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.navigation.BasicMove;
import team009.robot.TeamRobot;

public class HerdReturn extends Behavior {
    protected MapLocation pastureLocation;
    protected BasicMove move;
    public HerdReturn(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        move = new BasicMove(robot);
        move.setDestination(pastureLocation);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return robot.currentLoc.isAdjacentTo(pastureLocation);
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        move.move();
        return true;
    }
}

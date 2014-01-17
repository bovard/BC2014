package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;

public class ToyMoveToLocation extends Behavior {
    protected BugMove move;

    public ToyMoveToLocation(TeamRobot robot, MapLocation location) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(location);
    }

    public ToyMoveToLocation(TeamRobot robot) {
        super(robot);
        move = new BugMove(robot);
    }

    public void setDestination(MapLocation location) {
        if (move.destination != null && move.destination.equals(location)) {
            return;
        }
        move.setDestination(location);
    }

    @Override
    public boolean pre() throws GameActionException {
        return !move.atDestination();
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
    }

    @Override
    public boolean run() throws GameActionException {
        move.move();
        //TODO determine when to sneak based on if near friendly PASTR
        //move.sneak();
        return true;
    }
}
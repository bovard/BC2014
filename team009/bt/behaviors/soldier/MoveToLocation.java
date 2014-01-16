package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;

public class MoveToLocation extends Behavior {
    protected BugMove move;

    public MoveToLocation(TeamRobot robot, MapLocation location) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(location);
    }

    public MoveToLocation(TeamRobot robot) {
        super(robot);
        move = new BugMove(robot);
    }

    public void setDestination(MapLocation location) {
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

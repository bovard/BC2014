package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.MapUtils;
import team009.navigation.BasicMove;
import team009.robot.TeamRobot;

public class HerdSneak extends Behavior {
    protected Direction heardDirection;
    protected MapLocation pastureLocation;
    protected MapLocation startingLocation;
    protected boolean go;
    protected static final int MAX_DISTANCE_SQUARED = 226;

    protected BasicMove move;

    public HerdSneak(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);
        move = new BasicMove(robot);
        this.pastureLocation = pastureLocation;
        heardDirection = getNextDirection();
        startingLocation = pastureLocation.add(heardDirection);
        go = false;
    }

    @Override
    public boolean pre() throws GameActionException {
        // no prereqs
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return go && (!robot.rc.canMove(heardDirection)
                || robot.currentLoc.distanceSquaredTo(pastureLocation) > MAX_DISTANCE_SQUARED);
    }

    @Override
    public void reset() throws GameActionException {
        heardDirection = getNextDirection();
        startingLocation = pastureLocation.add(heardDirection);
        go = false;
    }

    @Override
    public boolean run() throws GameActionException {
        if (!go) {
            if (robot.currentLoc.equals(startingLocation)) {
                // see if we are in position
                go = true;
            } else {
                // get in position
                if (move.destination != startingLocation) {
                    move.setDestination(pastureLocation.add(heardDirection));
                }
                move.sneak();
            }
        }

        if (go) {
            // move
            if (robot.rc.canMove(heardDirection)) {
                robot.rc.sneak(heardDirection);
            } else {
                System.out.println("FORGOT TO CHECK POST NooB");
            }

        }

        return true;
    }


    private Direction getNextDirection() {
        // TODO: make this better
        return MapUtils.getRandomDir();
    }
}

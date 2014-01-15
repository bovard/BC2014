package team009.bt.behaviors.soldier;

import battlecode.common.*;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;

import java.util.ArrayList;

public class HerdSneak extends Behavior {
    protected Direction heardDirection;
    protected MapLocation pastureLocation;
    protected MapLocation startingLocation;
    protected ArrayList<Direction> possibleDirs = new ArrayList<Direction>();
    protected boolean go;
    protected static final int MAX_DISTANCE_SQUARED = 170;

    protected BugMove move;

    public HerdSneak(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);
        move = new BugMove(robot);
        this.pastureLocation = pastureLocation;
        populatePossibleDirs();
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
        int distToPasture = robot.currentLoc.distanceSquaredTo(pastureLocation);
        return go &&
                (!robot.rc.canMove(heardDirection)
                 || distToPasture > MAX_DISTANCE_SQUARED
                 || robot.currentLoc.distanceSquaredTo(robot.info.enemyHq) < distToPasture
                );
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
        return possibleDirs.get((int)(robot.rand.nextDouble()*possibleDirs.size()));
    }

    private void populatePossibleDirs() {
        for (Direction dir: Direction.values()) {
            if (!dir.equals(Direction.OMNI) && !dir.equals(Direction.NONE)) {
                if (MapUtils.isOnMap(pastureLocation.add(dir, 5), robot.info.width, robot.info.height)) {
                    possibleDirs.add(dir);
                }
            }
        }

    }
}

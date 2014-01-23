package team009.toyBT.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

import java.util.ArrayList;

public class ToyHerdSneak extends Behavior {
    protected Direction heardDirection;
    protected MapLocation startingLocation;
    protected Direction[] possibleDirs = new Direction[8];
    protected int dirLen;
    protected int currentDir = 0;
    protected boolean go;
    protected static final int MAX_DISTANCE_SQUARED = 170;

    protected BugMove move;
    protected ToySoldier soldier;
    protected boolean init = false;

    public ToyHerdSneak(ToySoldier robot) {
        super(robot);
        soldier = robot;

        move = new BugMove(robot);
        go = false;
    }

    @Override
    public boolean pre() throws GameActionException {
        // no prereqs
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        int distToPasture = robot.currentLoc.distanceSquaredTo(soldier.comLocation);
        return go &&
                (!robot.rc.canMove(heardDirection)
                 || distToPasture > MAX_DISTANCE_SQUARED
                 || robot.currentLoc.distanceSquaredTo(robot.info.enemyHq) < distToPasture
                );
    }

    @Override
    public void reset() throws GameActionException {
        currentDir =  (currentDir + 1) % dirLen;
        heardDirection = possibleDirs[currentDir];
        startingLocation = soldier.comLocation.add(heardDirection);
        go = false;
    }

    @Override
    public boolean run() throws GameActionException {
        if (!init) {
            populatePossibleDirs();
            heardDirection = possibleDirs[currentDir];
            startingLocation = soldier.comLocation.add(heardDirection);
        }

        if (!go) {
            if (robot.currentLoc.equals(startingLocation)) {
                // see if we are in position
                go = true;
            } else {
                // get in position
                if (move.destination != startingLocation) {
                    move.setDestination(soldier.comLocation.add(heardDirection));
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

    private void populatePossibleDirs() {
        int i = 0;
        for (Direction dir: Direction.values()) {
            if (!dir.equals(Direction.OMNI) && !dir.equals(Direction.NONE)) {
                if (MapUtils.isOnMap(soldier.comLocation.add(dir, 5), robot.info.width, robot.info.height)) {
                    possibleDirs[i++] = dir;
                }
            }
        }

        dirLen = i;
    }
}

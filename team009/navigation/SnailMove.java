package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.MapUtils;
import team009.lecture4.BasicPathing;
import team009.lecture4.BreadthFirst;
import team009.lecture4.VectorFunctions;
import team009.robot.TeamRobot;

import java.util.ArrayList;
import java.util.Random;

public class SnailMove extends Move {
    private MapLocation lastLoc;
    static Direction allDirections[] = Direction.values();
    static int directionalLooks[] = new int[]{0,1,-1,2,-2,3,-3,4};

    public SnailMove(TeamRobot robot) {
        super(robot);
        lastLoc = robot.currentLoc;
    }

    @Override
    public boolean move() throws GameActionException {
        stepsTaken++;
        return moveWrapper(false);
    }

    @Override
    public boolean sneak() throws GameActionException {
        stepsTaken++;
        return moveWrapper(true);
    }

    private boolean moveWrapper(boolean sneak) throws GameActionException {

        Direction toMove = null;
        if (destination != null) {
            toMove = calcMove();
        }

        if (toMove != null) {
            if (sneak) {
                robot.rc.sneak(toMove);
            } else {
                robot.rc.move(toMove);
            }
            return true;
        }
        return false;
    }

    public Direction calcMove() throws GameActionException
    {
        Direction sdir = robot.rc.getLocation().directionTo(destination);
        return BasicPathing.getMoveDirection(sdir, true, robot.rc, directionalLooks, allDirections);
    }


}

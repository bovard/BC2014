package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.lecture4.BasicPathing;
import team009.lecture4.BreadthFirst;
import team009.lecture4.VectorFunctions;
import team009.robot.TeamRobot;

import java.util.ArrayList;
import java.util.Random;

public class BreadthSnailMove extends Move {
    private MapLocation lastLoc;
    static Direction allDirections[] = Direction.values();
    static Random randall = new Random();
    static int directionalLooks[] = new int[]{0,1,-1,2,-2,3,-3,4};
    static ArrayList<MapLocation> path;
    static int bigBoxSize = 7;

    public BreadthSnailMove(TeamRobot robot) {
        super(robot);
        lastLoc = robot.currentLoc;

        //initialize the snail move
        randall.setSeed(robot.rc.getRobot().getID());
    }

    @Override
    public boolean move() throws GameActionException {
        return moveWrapper(false);
    }

    @Override
    public boolean sneak() throws GameActionException {
        return moveWrapper(true);
    }

    private boolean moveWrapper(boolean sneak) throws GameActionException {
        Direction toMove = calcMove();

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
        BreadthFirst.init(robot.rc, bigBoxSize);
        //MapLocation goal = getRandomLocation();
        path = BreadthFirst.pathTo(VectorFunctions.mldivide(robot.rc.getLocation(), bigBoxSize),
                VectorFunctions.mldivide(destination,bigBoxSize), 100000);
        //VectorFunctions.printPath(path,bigBoxSize);

        //follow breadthFirst path
        Direction bdir = BreadthFirst.getNextDirection(path, bigBoxSize);
        //BasicPathing.tryToMove(bdir, true, robot.rc, directionalLooks, allDirections);
        return BasicPathing.getMoveDirection(bdir, true, robot.rc, directionalLooks, allDirections);
    }
}

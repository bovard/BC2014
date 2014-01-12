package flow.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import flow.navigation.BugMove;
import flow.robot.soldier.Herder;

public class ComRetreatToPasture extends Behavior {
    Herder robot;
    MapLocation pastrLocation;
    BugMove move;

    public static final int ENEMY_COM_RANGE_RETREAT = 400;

    public ComRetreatToPasture(Herder robot, MapLocation pastrLocation) {
        super(robot);
        this.robot = robot;
        this.pastrLocation = pastrLocation;
        move = new BugMove(robot);
        move.setDestination(pastrLocation);
    }

    @Override
    public boolean pre() throws GameActionException {
        return robot.enemyComNear;
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
        if (!robot.currentLoc.isAdjacentTo(robot.info.hq)) {
            // Moves toward pastr
            move.move();
        }
        return true;
    }

}


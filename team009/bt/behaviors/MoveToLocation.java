package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.navigation.BasicMove;
import team009.robot.TeamRobot;

/**
 * Created by bovardtiberi on 1/3/14.
 */
public class MoveToLocation extends Behavior {
    protected BasicMove move;

    public MoveToLocation(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return robot.targetLocation != null;
    }

    @Override
    public boolean post() throws GameActionException {
        return robot.currentLoc.equals(move.destination);
    }

    @Override
    public void reset() throws GameActionException {
        move.setDestination(null);
    }

    @Override
    public boolean run() throws GameActionException {
        if (move.destination == null) {
            move.setDestination(robot.targetLocation);
        }

        move.move();
        return true;
    }
}

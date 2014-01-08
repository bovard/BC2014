package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.navigation.BasicMove;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.SoldierSpawner;

public class RetreatToPasture extends Behavior {
    BaseSoldier gs;
    boolean runningToDestination = false;
    MapLocation location;
    BasicMove move;

    public RetreatToPasture(BaseSoldier robot, MapLocation location) {
        super(robot);
        gs = robot;
        this.location = location;
        move = new BasicMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (Communicator.ReadRound(robot.round)) {
            GroupCommandDecoder decoder = Communicator.ReadFromGroup(rc, gs.group);

            // Time to retreat!
            if (decoder.data && decoder.command == SoldierSpawner.RETREAT_TO_PASTURE) {
                runningToDestination = true;
            }
        }

        // intentional >=
        return (runningToDestination || gs.seesEnemy) &&
                robot.currentLoc.distanceSquaredTo(location) >= CLOSE_TO_PASTURE;
    }

    @Override
    public boolean post() throws GameActionException {
        return robot.currentLoc.distanceSquaredTo(location) <= CLOSE_TO_PASTURE;
    }

    @Override
    public void reset() throws GameActionException {
        runningToDestination = false;
    }

    @Override
    public boolean run() throws GameActionException {

        // Checks if we need to inform team.
        if (!runningToDestination) {
            move.setDestination(location);
            runningToDestination = true;
            Communicator.WriteToGroup(rc, gs.group, SoldierSpawner.RETREAT_TO_PASTURE);
        }

        // Moves toward location
        move.move();

        return true;
    }

    public static final int CLOSE_TO_PASTURE = 6;
}


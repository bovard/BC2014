package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.MapUtils;
import team009.bt.decisions.SoldierSelector;
import team009.communication.Communicator;
import team009.robot.TeamRobot;

public class HQBalanced extends Behavior {
    private int pastureCount = 0;
    public HQBalanced(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        // There are never any preconditions that mean we shouldn't enter this state
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // This state never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // There is no state here so nothing to reset!

    }

    @Override
    public boolean run() throws GameActionException {
        // Spawn a guy at a random location
        if (robot.rc.isActive()) {
            Direction dir = Direction.NORTH;
            boolean done = false;
            int tries = 0;
            while(!done && tries < 8) {
                tries++;
                dir = MapUtils.getRandomDir();
                if (robot.rc.canMove(dir)) {
                    done = true;
                }
            }
            if (done) {

                if (Math.random() > 0.75) {
                    MapLocation pasture = new MapLocation(2, 2);
                    if (pastureCount == 1) {
                        pasture = new MapLocation(robot.info.width - 2, 2);
                    } else if (pastureCount == 2) {
                        pasture = new MapLocation(2, robot.info.height - 2);
                    } else if (pastureCount == 3) {
                        pasture = new MapLocation(robot.info.width - 2, robot.info.height - 2);
                        pastureCount = 0;
                    }
                    Communicator.WriteNewSoldier(rc, SoldierSelector.SOLDIER_TYPE_PASTURE, pasture);
                    pastureCount++;
                } else {
                    Communicator.WriteNewSoldier(rc, SoldierSelector.SOLDIER_TYPE_DUMB, new MapLocation(1, 1));
                }
                robot.rc.spawn(dir);
            }
        }
        return true;
    }
}

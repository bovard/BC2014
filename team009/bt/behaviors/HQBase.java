package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.robot.TeamRobot;

/**
 * Created by bovardtiberi on 1/6/14.
 */
public class HQBase extends Behavior {
    public HQBase(TeamRobot robot) {
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
            Direction [] allDirs = Direction.values();
            Direction dir = allDirs[((int)(Math.random()*8))];
            robot.rc.spawn(dir);
        }
        return true;
    }
}

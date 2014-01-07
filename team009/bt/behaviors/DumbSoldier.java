package team009.bt.behaviors;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.robot.TeamRobot;

public class DumbSoldier extends Behavior {
    private Direction direction = null;
    public DumbSoldier(TeamRobot robot, Direction direction) {
        super(robot);
        this.direction = direction;
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
        if (rc.isActive()) {
            if (rc.canMove(direction)) {
                rc.move(direction);
            } else {
                rc.setIndicatorString(0, "I AM STUCK!!!");
            }
        }
        return true;
    }
}


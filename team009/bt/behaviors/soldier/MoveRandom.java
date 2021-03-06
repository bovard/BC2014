package team009.bt.behaviors.soldier;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;

public class MoveRandom extends Behavior {

    public MoveRandom(TeamRobot robot) {
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
        // move randomly

        Direction dir = MapUtils.getRandomDir();
        boolean done = false;
        int tries = 0;
        while(!done && tries < 8) {
            tries++;
            if (robot.rc.canMove(dir)) {
                done = true;
            } else {
                dir = dir.rotateLeft();
            }
        }
        if (done) {
            rc.move(dir);
        }
        return true;
    }
}


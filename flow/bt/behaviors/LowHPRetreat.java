package flow.bt.behaviors;

import battlecode.common.GameActionException;
import flow.navigation.BugMove;
import flow.robot.soldier.BaseSoldier;

public class LowHPRetreat extends Behavior {
    public static final int LOW_HP = 30;
    public static final int HIGH_HP = 80;
    private boolean healing = false;
    protected BugMove move;

    public LowHPRetreat(BaseSoldier robot) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(robot.info.hq);
    }

    @Override
    public boolean pre() throws GameActionException {
        return robot.health < LOW_HP || healing;
    }

    @Override
    public boolean post() throws GameActionException {
        return robot.health > HIGH_HP;
    }

    @Override
    public void reset() throws GameActionException {
        healing = false;
    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: add smart retreat so we don't try to run by the enemy!
        healing = true;
        int dist = robot.currentLoc.distanceSquaredTo(robot.info.hq);
        if (dist < 9 && dist > 4) {
            return true;
        }
        move.move();
        return true;
    }
}

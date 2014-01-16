package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

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
        return ((BaseSoldier)robot).health < LOW_HP || healing;
    }

    @Override
    public boolean post() throws GameActionException {
        return ((BaseSoldier)robot).health > HIGH_HP;
    }

    @Override
    public void reset() throws GameActionException {
        healing = false;
    }

    @Override
    public boolean run() throws GameActionException {
        healing = true;
        int dist = robot.currentLoc.distanceSquaredTo(robot.info.hq);
        if (dist < 9 && dist > 4) {
            return true;
        }
        move.move();
        return true;
    }
}

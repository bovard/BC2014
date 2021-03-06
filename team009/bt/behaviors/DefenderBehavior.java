package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

public class DefenderBehavior extends Behavior {
    BugMove move;

    public DefenderBehavior(BaseSoldier soldier) {
        super(soldier);
        move = new BugMove(soldier);

        MapLocation hq = soldier.info.hq;
        Direction toDir = hq.directionTo(soldier.info.enemyHq);
        MapLocation defendLoc = hq.add(toDir).add(toDir).add(toDir).add(toDir);

        move.setDestination(defendLoc);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (!move.atDestination()) {
            move.move();
        }
        return false;
    }
}

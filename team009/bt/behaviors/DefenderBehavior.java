package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

public class DefenderBehavior extends Behavior {
    BugMove move;

    public DefenderBehavior(BaseSoldier soldier) {
        super(soldier);
        move = new BugMove(soldier);
        move.setDestination(soldier.info.hq.add(soldier.info.hq.directionTo(soldier.info.enemyHq)));
    }

    @Override
    public boolean pre() throws GameActionException {
        return !move.atDestination();
    }

    @Override
    public boolean run() throws GameActionException {
        move.move();

        return false;
    }
}

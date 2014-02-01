package team009.toyBT.micro;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class SuicideAvoid extends Behavior {
    private ToySoldier soldier;
    public SuicideAvoid(ToySoldier robot) {
        super(robot);
        soldier = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.nearestEnemyDistance <= 2;
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.setIndicatorString(2, "Avoiding suicider " + Clock.getRoundNum());
        Direction toMove = soldier.nearestEnemy.location.directionTo(soldier.currentLoc);

        if (soldier.rc.canMove(toMove)) {
            soldier.rc.move(toMove);
            return true;
        }

        if (soldier.rc.canMove(toMove.rotateLeft())) {
            soldier.rc.move(toMove.rotateLeft());
            return true;
        }

        if (soldier.rc.canMove(toMove.rotateRight())) {
            soldier.rc.move(toMove.rotateRight());
            return true;
        }

        return false;
    }
}

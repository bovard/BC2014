package team009.toyBT.behaviors;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.combat.Combat;
import team009.robot.soldier.ToySoldier;
import team009.utils.SmartNodeArray;
import team009.utils.SmartRobotInfoArray;

public class ToyEngageEnemy extends Behavior {
    ToySoldier soldier;
    Combat combat;
    public ToyEngageEnemy(ToySoldier robot) {
        super(robot);
        soldier = robot;
//        combat = new Combat(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.seesEnemyTeamNonHQRobot;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better Micro
        if (soldier.seesEnemySoldier) {
            if (attack(soldier.enemySoldiers) || _combatMove(soldier.rc, soldier.currentLoc, soldier.enemySoldiers.get(0).location)) {
                return true;
            }
        }
        if (soldier.seesEnemyPastr) {
            if (attack(soldier.enemyPastrs) || _combatMove(soldier.rc, soldier.currentLoc, soldier.enemyPastrs.get(0).location)) {
                return true;
            }
        }
        if (soldier.seesEnemyNoise) {
            if (attack(soldier.enemyNoise) || _combatMove(soldier.rc, soldier.currentLoc, soldier.enemyNoise.get(0).location)) {
                return true;
            }
        }

        return true;
    }

    private boolean attack(SmartRobotInfoArray arr) throws GameActionException {
        for (int i = 0; i < arr.length; i++) {
            RobotInfo info = arr.get(i);
            if (soldier.currentLoc.distanceSquaredTo(info.location) <= RobotType.SOLDIER.attackRadiusMaxSquared) {
                rc.attackSquare(info.location);
                return true;
            }
        }
        return false;
    }

    private boolean _combatMove(RobotController rc, MapLocation from, MapLocation to) throws GameActionException {
        Direction dirTo = from.directionTo(to);
        if (dirTo == Direction.OMNI || dirTo == Direction.NONE) {
            return false;
        }

        if (rc.canMove(dirTo)) {
            rc.move(dirTo);
            return true;
        }

        Direction leftTo = dirTo;
        Direction rightTo = dirTo;

        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            leftTo = leftTo.rotateLeft();
            if (rc.canMove(leftTo)) {
                rc.move(leftTo);
                return true;
            }

            rightTo = rightTo.rotateRight();
            if (rc.canMove(rightTo)) {
                rc.move(rightTo);
                return true;
            }
        }

        // Cannot move
        return false;
    }
}

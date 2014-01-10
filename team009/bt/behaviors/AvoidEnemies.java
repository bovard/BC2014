package team009.bt.behaviors;

import battlecode.common.*;
import team009.combat.CombatUtils;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

public class AvoidEnemies extends Behavior {
    private BugMove move;

    public AvoidEnemies(BaseSoldier robot) {
        super(robot);
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((BaseSoldier)robot).seesEnemy;
    }

    @Override
    public boolean post() throws GameActionException {
        return !((BaseSoldier)robot).seesEnemy;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        //TODO: Finish this!
        // we see an enemy
        MapLocation enemyCenter = CombatUtils.findCenterOfMass(((BaseSoldier)robot).enemies, robot.rc);
        Direction toMove = enemyCenter.directionTo(robot.currentLoc);
        Direction left = toMove.rotateLeft();
        Direction right = toMove.rotateRight();
        if (robot.rc.canMove(toMove)) {
            robot.rc.move(toMove);
            return true;
        } else if (robot.rc.canMove(left)) {
            robot.rc.move(left);
            return true;
        } else if (robot.rc.canMove(right)) {
            robot.rc.move(right);
            return true;
        } else {
            return false;
        }
    }
}

package team009.toyBT.micro;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.combat.CombatUtils;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class SoloEngageEnemies extends Behavior {
    private BugMove move;

    public SoloEngageEnemies(ToySoldier robot) {
        super(robot);
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemySoldier && ((ToySoldier)robot).allies.length == 0;
    }

    /**
     * If we are alone and we see an enemy soldier, run the f away! If you can't just stand there
     * @return
     * @throws GameActionException
     */
    @Override
    public boolean run() throws GameActionException {
        MapLocation enemyMass = CombatUtils.findCenterOfMass(((ToySoldier)robot).enemySoldiers.arr);
        Direction toMove = enemyMass.directionTo(((ToySoldier) robot).currentLoc);
        if (robot.rc.canMove(toMove)) {
            robot.rc.move(toMove);
            return true;
        }
        if (robot.rc.canMove(toMove.rotateRight())) {
            robot.rc.move(toMove.rotateRight());
            return true;
        }
        if (robot.rc.canMove(toMove.rotateLeft())) {
            robot.rc.move(toMove.rotateLeft());
            return true;
        }
        // otherwise just stand there!
        return false;
    }
}

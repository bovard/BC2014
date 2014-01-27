package team009.toyBT.micro;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class GroupEngageEnemies extends Behavior {
    public GroupEngageEnemies(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemySoldier && ((ToySoldier)robot).allies.length > 0;
    }

    @Override
    public boolean run() throws GameActionException {
        // if we outnumber them
        if (((ToySoldier)robot).enemySoldiers.length < ((ToySoldier)robot).friendlySoldiers.length) {
            // get next to our ally
            if (!((ToySoldier) robot).friendlySoldiers.arr[0].location.isAdjacentTo(((ToySoldier) robot).currentLoc)) {

            } else {

            }
            // then attack



        }

        // if they outnumber us
        // retreat!
        MapLocation nearestEnemyLoc = ((ToySoldier)robot).enemySoldiers.arr[0].location;
        Direction toMove = nearestEnemyLoc.directionTo(((ToySoldier) robot).currentLoc);
        if (robot.rc.canMove(toMove)) {
            robot.rc.move(toMove);
            return true;
        }
        if (robot.rc.canMove(toMove.rotateLeft())) {
            robot.rc.move(toMove.rotateLeft());
            return true;
        }
        if (robot.rc.canMove(toMove.rotateRight())) {
            robot.rc.move(toMove.rotateRight());
            return true;
        }


        System.out.println("Stuck in group engage");
        return false;
    }
}

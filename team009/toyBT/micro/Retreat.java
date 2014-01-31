package team009.toyBT.micro;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class Retreat extends Behavior {
    private ToySoldier soldier;
    public Retreat(ToySoldier robot) {
        super(robot);
        soldier = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        // if we are outnumbered, full health, and they aren't too close
        return soldier.enemySoldiers.length > soldier.friendlySoldiers.length + 2
                && soldier.enemySoldiers.arr[0].location.distanceSquaredTo(robot.currentLoc) > 5;
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.setIndicatorString(2, "Retreating " + Clock.getRoundNum());
        // we are here and outnumbered but we can move away possibly?
        // if we are surrounded this will work out very poorly
        MapLocation nearestEnemy = soldier.enemySoldiers.arr[0].location;
        Direction toEnemy = soldier.currentLoc.directionTo(nearestEnemy);
        Direction left = toEnemy.rotateLeft();
        Direction right = toEnemy.rotateRight();
        for (int i = soldier.enemySoldiersInCombatRange; --i>=0;) {
            Direction toNewEnemy = soldier.currentLoc.directionTo(soldier.enemySoldiers.arr[i].location);
            if (!(toNewEnemy == toEnemy) || !(toNewEnemy == left) || !(toNewEnemy == right)) {
                return false;
            }
        }

        if (robot.rc.canMove(toEnemy.opposite())) {
            robot.rc.move(toEnemy.opposite());
            return true;
        }

        return false;
    }
}

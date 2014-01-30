package team009.toyBT.micro;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class Suicide extends Behavior {
    // how many moves we made toward suicide
    private int moves;
    private ToySoldier soldier;
    public Suicide(ToySoldier robot) {
        super(robot);
        soldier = robot;
        moves = 0;
    }

    @Override
    public boolean pre() throws GameActionException {
        // TODO: add a comm channel where you say if they have micro to avoid suicide
        return soldier.enemySoldiers.length >= 3 && !soldier.seesEnemyHQ;
    }

    @Override
    public boolean run() throws GameActionException {
        MapLocation nearestEnemy = soldier.enemySoldiers.arr[0].location;
        Direction toEnemy = robot.currentLoc.directionTo(nearestEnemy);

        // we see at least 3 enemies, check to see if they are grouped enough to suicide
        // we've made it to the enemy!
        if (nearestEnemy.isAdjacentTo(soldier.currentLoc)) {
            int i = 1;
            while(i < soldier.enemySoldiers.length && soldier.enemySoldiers.arr[i].location.isAdjacentTo(soldier.currentLoc)) {
                i++;
            }
            if (soldier.enemySoldiersInCombatRange > soldier.health/ RobotType.SOLDIER.attackPower) {
                // they could kill us this turn, we have to suicide!
                soldier.suicide(i);
                return true;
            }
            // if we are adjacent to at least 2 enemies blow up!
            if (i>=2) {
                soldier.suicide(i);
                return true;
            }

            // otherwise let's try and get in a better square
            MapLocation left = robot.currentLoc.add(toEnemy.rotateLeft());
            MapLocation right = robot.currentLoc.add(toEnemy.rotateRight());

            if (soldier.enemySoldiers.arr[1].location.isAdjacentTo(left) && robot.rc.canMove(toEnemy.rotateLeft())) {
                robot.rc.move(toEnemy.rotateLeft());
                return true;
            }
            if (soldier.enemySoldiers.arr[1].location.isAdjacentTo(right) && robot.rc.canMove(toEnemy.rotateRight())) {
                robot.rc.move(toEnemy.rotateRight());
                return true;
            }

            // we can't, let's shoot someone instead!
            return false;
        }


        // we aren't next to an enemy but thinking about moving there
        // don't suicide if something is in your way!
        // prevents us from sending waves of suicides into the enemy
        // only a soldier who has a free 3 square in front of him will go
        // which means every other one max
        if (!soldier.rc.canMove(toEnemy) || !robot.rc.canMove(toEnemy.rotateLeft()) || !robot.rc.canMove(toEnemy.rotateRight())) {
            return false;
        }


        // if they aren't grouped up, don't do it man!
        if (robot.rc.senseObjectAtLocation(nearestEnemy.add(toEnemy.rotateLeft().rotateLeft())) == null || robot.rc.senseObjectAtLocation(nearestEnemy.add(toEnemy.rotateLeft().rotateLeft())) == null) {
            return false;
        }

        // we've never moved toward the enemy so they should be at most Max range away
        // we've already checked that we can move above
        if (moves == 0) {
            soldier.rc.move(toEnemy);
            moves++;
            return true;
        }

        int distanceToEnemey = soldier.currentLoc.distanceSquaredTo(nearestEnemy);

        // we've moved once toward an enemy so they should be at most 5 dist squared
        // if they're not that means that someone died or they are backing up
        // if they are backing up they have anti-suicide micro possibly?
        // subract 1 from global micro and abandon
        if (moves == 1) {
            if (distanceToEnemey > 5) {
                // TODO: decrement suicide counter
                moves = 0;
                return false;
            }
            soldier.rc.move(toEnemy);
            moves++;
            return true;
        }

        if (moves > 1) {
            // we should actually be adjacent to the enemy, looks like they are destroyed or backing away
            moves = 0;
            // TODO: decrement suicide counter
            return false;
        }

        return false;
    }
}

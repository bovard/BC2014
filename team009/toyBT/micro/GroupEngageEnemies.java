package team009.toyBT.micro;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class GroupEngageEnemies extends Behavior {
    private ToySoldier soldier;
    public GroupEngageEnemies(ToySoldier robot) {
        super(robot);
        soldier = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemySoldier && ((ToySoldier)robot).friendlySoldiers.length > 0;
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.setIndicatorString(2, "Group Engage " + Clock.getRoundNum());
        MapLocation nearestAlly = soldier.nearestAlly.location;
        Direction toAlly = soldier.currentLoc.directionTo(nearestAlly);
        // if we outnumber them
        if (((ToySoldier)robot).enemySoldiers.length < ((ToySoldier)robot).friendlySoldiers.length + 1) {
            // if we aren't next to our ally get there!
            if (!nearestAlly.isAdjacentTo(((ToySoldier) robot).currentLoc)) {
                robot.rc.setIndicatorString(2, "Not adjacent!" + ((ToySoldier) robot).currentLoc + " " + ((ToySoldier) robot).friendlySoldiers.arr[0].location);
                if (robot.rc.canMove(toAlly)) {
                    robot.rc.move(toAlly);
                    return true;
                }
                if (Clock.getRoundNum() % 2 == 0) {
                    if (robot.rc.canMove(toAlly.rotateLeft())) {
                        robot.rc.move(toAlly.rotateLeft());
                        return true;
                    }
                    if (robot.rc.canMove(toAlly.rotateRight())) {
                        robot.rc.move(toAlly.rotateRight());
                        return true;
                    }

                } else {
                    if (robot.rc.canMove(toAlly.rotateRight())) {
                        robot.rc.move(toAlly.rotateRight());
                        return true;
                    }

                    if (robot.rc.canMove(toAlly.rotateLeft())) {
                        robot.rc.move(toAlly.rotateLeft());
                        return true;
                    }
                }
                robot.rc.setIndicatorString(2, "Can't get adjacent!" + ((ToySoldier) robot).currentLoc + " " + ((ToySoldier) robot).friendlySoldiers.arr[0].location);
                return false;
            }

            MapLocation nearestEnemy = soldier.nearestEnemy.location;
            Direction toEnemy = soldier.currentLoc.directionTo(nearestEnemy);

            // we're next to our ally, try to form a wall toward the nearest enemy
            MapLocation leftLoc = nearestAlly.add(toEnemy.rotateLeft().rotateLeft());
            Direction toLeft = soldier.currentLoc.directionTo(leftLoc);
            MapLocation rightLoc = nearestAlly.add(toEnemy.rotateRight().rotateRight());
            Direction toRight = soldier.currentLoc.directionTo(rightLoc);

            robot.rc.setIndicatorString(2, "Trying to get to a wall!" + ((ToySoldier) robot).currentLoc + " " + ((ToySoldier) robot).friendlySoldiers.arr[0].location);
            // try to move in position to the left
            if (soldier.currentLoc.distanceSquaredTo(leftLoc) < soldier.currentLoc.distanceSquaredTo(rightLoc)) {
                if (soldier.currentLoc.isAdjacentTo(leftLoc) && soldier.rc.canMove(toLeft)) {
                    soldier.rc.move(toLeft);
                    return true;
                }
            } else {
            // try to move in position to the right
                if (soldier.currentLoc.isAdjacentTo(rightLoc) && soldier.rc.canMove(toRight)) {
                    soldier.rc.move(toRight);
                    return true;
                }

            }

            robot.rc.setIndicatorString(2, "Moving into range! " + Clock.getRoundNum());
            // then move into range!
            if (robot.rc.canMove(toEnemy)) {
                robot.rc.move(toEnemy);
                return true;
            }

            if (robot.rc.canMove(toEnemy.rotateLeft())) {
                robot.rc.move(toEnemy.rotateLeft());
                return true;
            }

            if (robot.rc.canMove(toEnemy.rotateRight())) {
                robot.rc.move(toEnemy.rotateRight());
                return true;
            }



        }

        // if they outnumber us
        robot.rc.setIndicatorString(2, "We are outnumbered! " + ((ToySoldier) robot).enemySoldiers.length + " > " + ((ToySoldier) robot).friendlySoldiers.length + 1);
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

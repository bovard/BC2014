package team009.toyBT.micro;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class HQEngageEnemies extends Behavior{
    ToySoldier soldier;
    private int hqMaxDistance = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared + 1) + 1, 2) - 1;
    public HQEngageEnemies(ToySoldier robot) {
        super(robot);
        soldier = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.seesEnemyHQ && soldier.seesEnemySoldier;
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.setIndicatorString(2, "HQ Engage " + Clock.getRoundNum());
        Direction toEnemyHQ = soldier.currentLoc.directionTo(soldier.info.enemyHq);

        if (robot.currentLoc.distanceSquaredTo(soldier.info.enemyHq) <= hqMaxDistance) {
            // move out of there!
            Direction opposite = toEnemyHQ.opposite();
            if (robot.rc.canMove(opposite)) {
                robot.rc.move(opposite);
                return true;
            } else if (robot.rc.canMove(opposite.rotateRight())) {
                robot.rc.move(opposite.rotateRight());
                return true;
            } else if (robot.rc.canMove(opposite.rotateLeft())) {
                robot.rc.move(opposite.rotateLeft());
                return true;
            } else if (robot.rc.canMove(opposite.rotateLeft().rotateLeft())) {
                robot.rc.move(opposite.rotateLeft().rotateLeft());
                return true;
            } else if (robot.rc.canMove(opposite.rotateRight().rotateRight())) {
                robot.rc.move(opposite.rotateRight().rotateRight());
                return true;
            }
            System.out.println("Oh noes!");
            return false;
        }


        MapLocation nearestAlly = soldier.nearestAlly.location;
        if (soldier.nearestAlly != null) {
            nearestAlly = soldier.nearestAlly.location;
        }
        Direction toAlly = soldier.currentLoc.directionTo(nearestAlly);
        Direction toEnemyHQLeft = toEnemyHQ.rotateLeft();
        Direction toEnemyHQRight = toEnemyHQ.rotateRight();

        // if we outnumber them!
        if (soldier.enemySoldiers.length < soldier.alliedSoldiersInCombatRange + 1) {
            if (soldier.nearestAlly == null && !soldier.nearestAlly.location.isAdjacentTo(((ToySoldier) robot).currentLoc)) {
                Direction toTry = toAlly;
                if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                    robot.rc.move(toTry);
                    return true;
                }

                toTry = toAlly.rotateRight();
                if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                    robot.rc.move(toTry);
                    return true;
                }

                toTry = toAlly.rotateLeft();
                if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                    robot.rc.move(toTry);
                    return true;
                }

                toTry = toAlly.rotateLeft().rotateLeft();
                if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                    robot.rc.move(toTry);
                    return true;
                }


                toTry = toAlly.rotateRight().rotateRight();
                if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                    robot.rc.move(toTry);
                    return true;
                }

                robot.rc.setIndicatorString(2, "HQ Can't get adjacent!" + ((ToySoldier) robot).currentLoc + " " + ((ToySoldier) robot).friendlySoldiers.arr[0].location);
                return true;
            }

            MapLocation nearestEnemy = soldier.nearestEnemy.location;
            Direction toEnemy = soldier.currentLoc.directionTo(nearestEnemy);

            robot.rc.setIndicatorString(2, "HQ Moving into range! " + Clock.getRoundNum());
            // then move into range!

            Direction toTry = toEnemy;
            if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                robot.rc.move(toTry);
                return true;
            }

            toTry = toEnemy.rotateRight();
            if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                robot.rc.move(toTry);
                return true;
            }

            toTry = toEnemy.rotateLeft();
            if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                robot.rc.move(toTry);
                return true;
            }

            toTry = toEnemy.rotateLeft().rotateLeft();
            if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                robot.rc.move(toTry);
                return true;
            }


            toTry = toEnemy.rotateRight().rotateRight();
            if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
                robot.rc.move(toTry);
                return true;
            }

        }


        // if they outnumber us
        robot.rc.setIndicatorString(2, "HQ We are outnumbered! " + ((ToySoldier) robot).enemySoldiers.length + " > " + ((ToySoldier) robot).friendlySoldiers.length + 1);
        // retreat!
        MapLocation nearestEnemyLoc = soldier.nearestEnemy.location;
        Direction toMove = nearestEnemyLoc.directionTo(((ToySoldier) robot).currentLoc);

        Direction toTry = toMove;
        if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
            robot.rc.move(toTry);
            return true;
        }

        toTry = toMove.rotateRight();
        if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
            robot.rc.move(toTry);
            return true;
        }

        toTry = toMove.rotateLeft();
        if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
            robot.rc.move(toTry);
            return true;
        }

        toTry = toMove.rotateLeft().rotateLeft();
        if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
            robot.rc.move(toTry);
            return true;
        }


        toTry = toMove.rotateRight().rotateRight();
        if (toTry != toEnemyHQ && toTry != toEnemyHQLeft && toTry != toEnemyHQRight && robot.rc.canMove(toTry)) {
            robot.rc.move(toTry);
            return true;
        }

        robot.rc.setIndicatorString(2, "HQ Couldn't move away " + Clock.getRoundNum());

        return true;
    }


}

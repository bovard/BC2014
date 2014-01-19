package team009.toyBT;

import battlecode.common.*;
import team009.combat.CombatUtils;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;
import team009.utils.SmartRobotInfoArray;

public class ToyCombat {
    private ToySoldier soldier;
    private int sensorRadius, attackRadius, halfRange, range;
    private BugMove move;

    public ToyCombat(ToySoldier robot) {
        soldier = robot;
        _init();
    }

    private void _init() {
        move = new BugMove(soldier);
        sensorRadius = RobotType.SOLDIER.sensorRadiusSquared;
        attackRadius = RobotType.SOLDIER.attackRadiusMaxSquared;
        halfRange = (int)Math.sqrt(attackRadius);
        range = halfRange * 2;
    }

    /**
     * Will attempt to attack robot enemies around
     * @return
     * @throws GameActionException
     */
    public void attack() throws GameActionException {
        RobotController rc = soldier.rc;
        MapLocation currentLoc = soldier.currentLoc;

        // Simple first check.  Friendsly nearby
        SmartRobotInfoArray nmeInfos = soldier.enemySoldiers;
        MapLocation[] nmeLocs = new MapLocation[soldier.enemySoldiers.length];
        int[] nmeHps = new int[soldier.enemySoldiers.length];

        _fillData(nmeInfos, nmeLocs, nmeHps);
        _sort(nmeInfos, nmeLocs, nmeHps);

        rc.setIndicatorString(0, "isMoving: " + isMoving(currentLoc) + " : " + move.destination);
        if (isMoving(currentLoc)) {

            // have i moved into any enemies
            MapLocation nearestAttacker = _getLowestHPAttackableEnemy(currentLoc, nmeLocs);
            if (nearestAttacker == null) {
                rc.setIndicatorString(1, "Moving: " + move.destination);
                move.move();
            } else {
                rc.setIndicatorString(1, "nearestAttacker: " + nearestAttacker);
                rc.attackSquare(nearestAttacker);
            }
        } else {

            if (soldier.enemySoldiers.length > 0) {
                // Out numbered or even.  Wait for them to attack, then attack!
                // TODO: Review Bovard
                if (soldier.friendlySoldiers.length <= soldier.enemySoldiers.length) {
                    MapLocation nearestEnemy = _getLowestHPAttackableEnemy(currentLoc, nmeLocs);
                    if (nearestEnemy == null) {
                        // Can we move closer?
                        Direction to = _combatMove(rc, currentLoc, nmeLocs[0]);
                        if (to != null) {
                            rc.move(to);
                        }
                    } else {
                        // They are in attack range. defend position!
                        rc.attackSquare(nearestEnemy);
                    }
                }

                // We outnumber them
                // TODO: Review Bovard
                else {
                    MapLocation nearestEnemy = _getLowestHPAttackableEnemy(currentLoc, nmeLocs);
                    if (nearestEnemy == null) {
                        Direction move = _combatMove(rc, currentLoc, nmeLocs[0]);
                        if (move != null) {
                            rc.move(move);
                        }
                    } else {
                        // They are in attack range. defend position!
                        rc.attackSquare(nearestEnemy);
                    }
                }
            } else {
                if (soldier.enemyPastrs.length > 0) {
                    _moveOrAttack(rc, currentLoc, soldier.enemyPastrs.arr[0].location);
                } else if (soldier.enemyNoise.length > 0) {
                    _moveOrAttack(rc, currentLoc, soldier.enemyNoise.arr[0].location);
                }
            }
        }
    }

    // If the robot is moving with bug
    public boolean isMoving(MapLocation curr) {
        return move.destination != null ? move.destination.distanceSquaredTo(curr) > attackRadius: false;
    }

    private void _moveOrAttack(RobotController rc, MapLocation from, MapLocation to) throws GameActionException {
        // We got to just fight it out
        if (rc.canAttackSquare(to)) {
            rc.attackSquare(to);
        } else {
            rc.move(_combatMove(rc, from, to));
        }
    }

    // Validates if there are any nearest attackers.  Always go for the nearest attackers first.
    private MapLocation _getLowestHPAttackableEnemy(MapLocation loc, MapLocation[] enemies) {
        for (int i = 0; i < enemies.length; i++) {
            if (loc.distanceSquaredTo(enemies[i]) <= attackRadius) {
                return enemies[i];
            }
        }

        return null;
    }

    // If the current location is attackable by any enemy
    private boolean _isAttackablePosition(MapLocation myLoc, MapLocation[] enemies) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].distanceSquaredTo(myLoc) <= attackRadius) {
                return true;
            }
        }

        return false;
    }

    private Direction _combatMove(RobotController rc, MapLocation from, MapLocation to) throws GameActionException {
        Direction dirTo = from.directionTo(to);
        if (dirTo == Direction.OMNI || dirTo == Direction.NONE) {
            return null;
        }

        if (rc.canMove(dirTo)) {
            return dirTo;
        }

        Direction leftTo = dirTo;
        Direction rightTo = dirTo;

        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            leftTo = leftTo.rotateLeft();
            if (rc.canMove(leftTo)) {
                return leftTo;
            }

            rightTo = rightTo.rotateRight();
            if (rc.canMove(rightTo)) {
                return rightTo;
            }
        }

        // Cannot move
        return null;
    }

    /**
     * Determines if bug is required
     */
    private boolean mustUseBug(RobotController rc, MapLocation from, MapLocation to) {
        return false;
    }

    private void _fillData(SmartRobotInfoArray infos, MapLocation[] locs, int[] hps) throws GameActionException {
        int len = infos.length;

        for (int i = 0; i < len; i++) {
            locs[i] = infos.arr[i].location;
            hps[i] = (int)infos.arr[i].health;
        }
    }

    private void _sort(SmartRobotInfoArray infos, MapLocation[] enemyLocs, int[] enemyHps) {
        int currentHp;
        MapLocation currentLoc;
        RobotInfo currentInfo;
        for (int i = 1, j, len = enemyLocs.length; i < len; i++) {
            currentHp = enemyHps[i];
            currentLoc = enemyLocs[i];
            currentInfo = infos.arr[i];

            for (j = i; j > 0 && currentHp < enemyHps[j - 1]; j--) {
                enemyLocs[j] = enemyLocs[j - 1];
                enemyHps[j] = enemyHps[j - 1];
                infos.arr[j] = infos.arr[j - 1];
            }

            enemyHps[j] = currentHp;
            enemyLocs[j] = currentLoc;
            infos.arr[j] = currentInfo;
        }
    }
}


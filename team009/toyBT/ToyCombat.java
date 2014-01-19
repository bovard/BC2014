package team009.toyBT;

import battlecode.common.*;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;
import team009.utils.SmartMapLocationArray;
import team009.utils.SmartRobotInfoArray;

public class ToyCombat {
    private ToySoldier soldier;
    private int sensorRadius, attackRadius, halfRange, range;
    private BugMove move;
    private int hqMaxDistance = (int)Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 1;
    private SmartMapLocationArray currentAttackableEnemies;

    public ToyCombat(ToySoldier robot) {
        soldier = robot;
        _init();
        hqMaxDistance *= hqMaxDistance;
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
        SmartMapLocationArray currentAttackableEnemies = this.currentAttackableEnemies = _getAttackableEnemies(currentLoc, nmeLocs);
        MapLocation nearestEnemy = currentAttackableEnemies.arr[0];

        if (move.destination != null) {

            // have i moved into any enemies
            if (nearestEnemy == null) {
                Direction dir = _combatMove(rc, currentLoc, move.destination, nmeLocs);
                if (dir != null) {
                    rc.move(dir);
                }
            } else {
                move.destination = null;
            }
            return;
        }

        if (soldier.enemySoldiers.length > 0) {
            // Out numbered or even.  Wait for them to attack, then attack!
            // TODO: Make a real decision on what is best.
            if (soldier.friendlySoldiers.length <= soldier.enemySoldiers.length) {
                MapLocation target = nearestEnemy == null ? nmeLocs[0] : nearestEnemy;
                _moveOrAttack(rc, currentLoc, target, nmeLocs);
            }

            // We outnumber them
            // TODO: Make a real decision on what is best.
            else {
                MapLocation target = nearestEnemy == null ? nmeLocs[0] : nearestEnemy;
                _moveOrAttack(rc, currentLoc, target, nmeLocs);
            }
        } else {
            if (soldier.enemyPastrs.length > 0) {
                MapLocation target = soldier.enemyPastrs.arr[0].location;
                _moveOrAttack(rc, currentLoc, target, nmeLocs);
            } else if (soldier.enemyNoise.length > 0) {
                MapLocation target = soldier.enemyNoise.arr[0].location;
                _moveOrAttack(rc, currentLoc, target, nmeLocs);
            }
        }
    }

    private void _moveOrAttack(RobotController rc, MapLocation from, MapLocation to, MapLocation[] enemies) throws GameActionException {


        // We got to just fight it out
        if (rc.canAttackSquare(to)) {
            rc.attackSquare(to);
        } else {
            move.setDestination(to);
            Direction dir = _combatMove(rc, from, to, enemies);
            if (dir != null) {
                rc.move(dir);
            }
        }
    }

    // Validates if there are any nearest attackers.  Always go for the nearest attackers first.
    private SmartMapLocationArray _getAttackableEnemies(MapLocation loc, MapLocation[] enemies) {
        SmartMapLocationArray arr = new SmartMapLocationArray();
        for (int i = 0; i < enemies.length; i++) {
            if (loc.distanceSquaredTo(enemies[i]) <= attackRadius) {
                arr.add(enemies[i]);
            }
        }

        return arr;
    }

    private Direction _combatMove(RobotController rc, MapLocation from, MapLocation to, MapLocation[] enemies) throws GameActionException {
        Direction dirTo = move.calcMove();

        if (rc.canMove(dirTo) && !_tooDangerous(from.add(dirTo), enemies)) {
            return dirTo;
        }

        boolean right = from.x > to.x;
        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            dirTo = right ? dirTo.rotateRight() : dirTo.rotateLeft();
            if (rc.canMove(dirTo) && !_tooDangerous(from.add(dirTo), enemies)) {
                return dirTo;
            }
        }

        // Cannot move
        return null;
    }

    private void _fillData(SmartRobotInfoArray infos, MapLocation[] locs, int[] hps) throws GameActionException {
        int len = infos.length;

        for (int i = 0; i < len; i++) {
            locs[i] = infos.arr[i].location;
            hps[i] = (int)infos.arr[i].health;
        }
    }

    // If the location provided is to dangerous
    private boolean _tooDangerous(MapLocation loc, MapLocation[] enemies) {
        if (soldier.info.enemyHq.distanceSquaredTo(loc) <= hqMaxDistance) {
            return true;
        }

        // Our move is worse
        // TODO: This may be a bad decision
        SmartMapLocationArray arr = _getAttackableEnemies(loc, enemies);
        if (arr.length > currentAttackableEnemies.length) {
            return true;
        }

        return false;
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


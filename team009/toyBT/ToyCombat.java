package team009.toyBT;

import battlecode.common.*;
import team009.RobotInformation;
import team009.combat.CombatUtils;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;
import team009.utils.HQAttackUtil;
import team009.utils.SmartMapLocationArray;
import team009.utils.SmartRobotInfoArray;

public class ToyCombat {
    private ToySoldier soldier;
    private int sensorRadius, attackRadius, oneAwayAttackRadius, halfRange, range;
    private BugMove move;
    private int hqMaxDistance = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared + 1) + 1, 2) - 1;
    private SmartMapLocationArray currentAttackableEnemies;

    public ToyCombat(ToySoldier robot) {
        soldier = robot;
        _init();
    }

    private void _init() {
        move = new BugMove(soldier);
        sensorRadius = RobotType.SOLDIER.sensorRadiusSquared;
        attackRadius = RobotType.SOLDIER.attackRadiusMaxSquared;
        oneAwayAttackRadius = (int)Math.sqrt(attackRadius) + 1;
        oneAwayAttackRadius *= oneAwayAttackRadius;
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
        MapLocation nmeCentroid = CombatUtils.findCenterOfMass(nmeLocs);

        if (move.destination != null && !_canAttackPastrOrNoise(rc)) {

            // have i moved into any enemies
            if (nearestEnemy == null ) {
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
            // We are outnumbered.
            if (soldier.friendlySoldiers.length < soldier.enemySoldiers.length) {
                MapLocation target = nearestEnemy == null ? nmeLocs[0] : nearestEnemy;
                // Move closer without getting into attackable range.
                // TODO: Add if a friend is in firing range, move toward the enemy
                // TODO: Group Centroid channel?  It may come in handy here.
                if (nearestEnemy == null && !soldier.hqAttack.inProximity(currentLoc)) {
                    Direction dir = _combatAvoid(rc, currentLoc, nmeCentroid, nmeLocs);
                    if (dir != null) {
                        rc.move(dir);
                    }
                } else {
                    _moveOrAttack(rc, currentLoc, target, nmeLocs);
                }
            }

            // We outnumber or equal
            else {

                rc.setIndicatorString(2, "Location: " + soldier.hqAttack.inProximity(currentLoc) + " : nearestEnemy: " + nearestEnemy + " : " + soldier.hqAttack.toClose(nmeCentroid));
                // If the enemy centroid is within enemy hq range, do not engage first
                if (soldier.hqAttack.inProximity(currentLoc) && soldier.hqAttack.toClose(nmeCentroid)) {
                    if (nearestEnemy != null) {
                        rc.attackSquare(nearestEnemy);
                    }
                } else {
                    MapLocation target = nearestEnemy == null ? nmeLocs[0] : nearestEnemy;
                    _moveOrAttack(rc, currentLoc, target, nmeLocs);
                }
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

    // If the current location is attackable by any enemy
    private boolean _isAttackablePosition(MapLocation myLoc, MapLocation[] enemies) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].distanceSquaredTo(myLoc) <= attackRadius) {
                return true;
            }
        }

        return false;
    }

    private Direction _combatMove(RobotController rc, MapLocation from, MapLocation to, MapLocation[] enemies) throws GameActionException {
        Direction dirTo = move.calcMove();

        if (dirTo == null) {
            return null;
        }

        MapLocation newLoc = from.add(dirTo);
        HQAttackUtil hqAttack = soldier.hqAttack;
        if (rc.canMove(dirTo) && !_tooDangerous(newLoc, enemies) && !hqAttack.toClose(newLoc)) {
            return dirTo;
        }

        Direction leftTo = dirTo;
        Direction rightTo = dirTo;

        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            leftTo = leftTo.rotateLeft();
            newLoc = from.add(leftTo);
            if (rc.canMove(leftTo) && !_tooDangerous(newLoc, enemies) && !hqAttack.toClose(newLoc)) {
                return leftTo;
            }

            rightTo = rightTo.rotateRight();
            newLoc = from.add(rightTo);
            if (rc.canMove(rightTo) && !_tooDangerous(newLoc, enemies) && !hqAttack.toClose(newLoc)) {
                return rightTo;
            }
        }

        // Cannot move
        return null;
    }

    private Direction _combatAvoid(RobotController rc, MapLocation from, MapLocation nmeCentroid, MapLocation[] enemies) throws GameActionException {
        Direction dirTo = nmeCentroid.directionTo(from);
        if (rc.canMove(dirTo) && !_isAttackablePosition(from.add(dirTo), enemies)) {
            return dirTo;
        }

        Direction rightTo = dirTo;
        Direction leftTo = dirTo;

        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            leftTo = leftTo.rotateLeft();
            if (rc.canMove(leftTo) && !_isAttackablePosition(from.add(leftTo), enemies)) {
                return leftTo;
            }

            rightTo = rightTo.rotateRight();
            if (rc.canMove(rightTo) && !_isAttackablePosition(from.add(rightTo), enemies)) {
                return rightTo;
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

        // Our move is worse
        // TODO: This may be a bad decision
        SmartMapLocationArray arr = _getAttackableEnemies(loc, enemies);
        if (arr.length > currentAttackableEnemies.length + 1) {
            return true;
        }

        return false;
    }

    private boolean _canAttackPastrOrNoise(RobotController rc) {
        for (int i = 0; i < soldier.enemyPastrs.length; i++) {
            if (rc.canAttackSquare(soldier.enemyPastrs.arr[i].location)) {
                return true;
            }
        }
        for (int i = 0; i < soldier.enemyNoise.length; i++) {
            if (rc.canAttackSquare(soldier.enemyNoise.arr[i].location)) {
                return true;
            }
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

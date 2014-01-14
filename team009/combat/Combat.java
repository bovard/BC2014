package team009.combat;

import battlecode.common.*;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

public class Combat {
    private BaseSoldier soldier;
    private int sensorRadius, attackRadius, halfRange, range;
    private BugMove move;

    public Combat(BaseSoldier robot) {
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
     * @param rc
     * @param enemies
     * @param currentLoc
     * @return
     * @throws GameActionException
     */
    public void attack(RobotController rc, Robot[] enemies, MapLocation currentLoc) throws GameActionException {

        // Simple first check.  Friendsly nearby
        Robot[] friends = rc.senseNearbyGameObjects(Robot.class, currentLoc, sensorRadius, rc.getTeam());
        int we = friends.length + 1;
        RobotInfo[] nmeInfos = new RobotInfo[enemies.length];
        MapLocation[] nmeLocs = new MapLocation[enemies.length];
        int[] nmeHps = new int[enemies.length];
        _fillData(rc, enemies, nmeInfos, nmeLocs, nmeHps);

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
            move.destination = null;


            rc.setIndicatorString(1, "Enemies: " + we + " > " + enemies.length);
            MapLocation nearestAttacker = _getLowestHPAttackableEnemy(currentLoc, nmeLocs);

            // Simple attack scheme.  If we outnumber, we win
            if (we > enemies.length) {

                // we must bug move
                if (nearestAttacker == null) {
                    rc.setIndicatorString(2, "nearestAttacker: " + nearestAttacker);
                    move.setDestination(nmeLocs[0]);
                    move.move();
                } else {
                    // attack nearest lowest hp attacker
                    rc.attackSquare(nearestAttacker);
                    rc.setIndicatorString(2, "attacking: " + nearestAttacker);
                }
            }

            // Lets attack if we are the same.
            else if (we == enemies.length) {

                rc.setIndicatorString(1, "We are equal!: ");

                if (nearestAttacker == null) {
                    boolean attack = false;
                    MapLocation loc = null;

                    // Are there any friendlies attackable.
                    for (int i = 0; i < friends.length; i++) {
                        RobotInfo friend = rc.senseRobotInfo(friends[i]);
                        if (_isAttackablePosition(friend.location, nmeLocs)) {

                            // lets attack!
                            attack = true;
                            loc = _getLowestHPAttackableEnemy(friend.location, nmeLocs);
                            break;
                        }
                    }

                    if (attack) {
                        if (nearestAttacker == null) {
                            Direction dir = _combatMove(rc, currentLoc, loc);
                            rc.move(dir);
                            rc.setIndicatorString(2, "Moving at attacker " + dir);
                        } else {
                            rc.attackSquare(nearestAttacker);
                            rc.setIndicatorString(2, "attacking " + nearestAttacker);
                        }
                    }
                } else {
                    rc.attackSquare(nearestAttacker);
                }
            }

            // We must stay out of the range of the enemies then.  If we cannot, then we fight.
            else {
                boolean isAttackable = _isAttackablePosition(currentLoc, nmeLocs);
                if (isAttackable) {
                    Direction away = CombatUtils.findCenterOfMass(nmeLocs).directionTo(currentLoc);
                    Direction moveDir = _combatMove(rc, currentLoc, currentLoc.add(away));

                    // Cannot move, must engage.
                    if (moveDir == null || moveDir != null && _isAttackablePosition(currentLoc.add(moveDir), nmeLocs)) {
                        rc.attackSquare(nearestAttacker);
                        rc.setIndicatorString(2, "with near attacker: " + nearestAttacker);
                    }

                    // We can move and get out of the way. Lets do it.
                    else {
                        rc.setIndicatorString(2, "with move ability: " + moveDir);
                        rc.move(moveDir);
                    }
                }

                // Else, do nothing
                else {
                    rc.setIndicatorString(2, "with Do Nothing: ");
                }
            }
        }
    }

    // If the robot is moving with bug
    public boolean isMoving(MapLocation curr) {
        return move.destination != null ? move.destination.distanceSquaredTo(curr) > attackRadius: false;
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
                rc.move(rightTo);
                return rightTo;
            }
        }

        // Cannot move
        return null;
    }

    private void _fillData(RobotController rc, Robot[] enemies, RobotInfo[] infos, MapLocation[] locs, int[] hps) throws GameActionException {
        int len = infos.length;

        for (int i = 0; i < len; i++) {
            RobotInfo info = rc.senseRobotInfo(enemies[i]);
            infos[i] = info;
            locs[i] = infos[i].location;
            hps[i] = (int)infos[i].health;
        }
        _sort(enemies, infos, locs, hps);
    }

    private void _sort(Robot[] enemies, RobotInfo[] infos, MapLocation[] enemyLocs, int[] enemyHps) {
        int currentHp;
        MapLocation currentLoc;
        Robot currentEnemy;
        RobotInfo currentInfo;
        for (int i = 1, j, len = enemyLocs.length; i < len; i++) {
            currentHp = enemyHps[i];
            currentLoc = enemyLocs[i];
            currentEnemy = enemies[i];
            currentInfo = infos[i];

            for (j = i; j > 0 && currentHp < enemyHps[j - 1]; j--) {
                enemyLocs[j] = enemyLocs[j - 1];
                enemyHps[j] = enemyHps[j - 1];
                enemies[j] = enemies[j - 1];
                infos[j] = infos[j - 1];
            }

            enemyHps[j] = currentHp;
            enemyLocs[j] = currentLoc;
            enemies[j] = currentEnemy;
            infos[j] = currentInfo;
        }
    }
}

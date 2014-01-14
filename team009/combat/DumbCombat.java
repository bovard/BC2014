package team009.combat;
import battlecode.common.*;

public class DumbCombat {
    private static boolean init = false;
    private static int attackRadius, halfRange, range;

    public static void Attack(RobotController rc, Robot[] enemies, MapLocation currentLoc) throws GameActionException {

        // TeamRobot run takes care of "isActive" check.
        for (int i = 0; i < enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(enemies[i]);

            if (info.type == RobotType.HQ) {
                continue;
            }

            // TODO: Actual micro!
            if (rc.canAttackSquare(info.location)) {
                rc.attackSquare(info.location);
                break;
            } else {
                Direction dir = currentLoc.directionTo(info.location);
                if (rc.canMove(dir)) {
                    rc.move(dir);
                    break;
                }
            }
        }
    }
    public static void LessDumbAttack(RobotController rc, Robot[] enemies, MapLocation currentLoc) throws GameActionException {
        if (!init) {
            _init(rc);
        }

        // Simple first check.  Friendsly nearby
        Robot[] friends = rc.senseNearbyGameObjects(Robot.class, currentLoc, attackRadius, rc.getTeam());
        int we = friends.length + 1;
        RobotInfo[] nmeInfos = new RobotInfo[enemies.length];
        MapLocation[] nmeLocs = new MapLocation[enemies.length];
        int[] nmeHps = new int[enemies.length];
        _fillData(rc, enemies, nmeInfos, nmeLocs, nmeHps);

        // Simple attack scheme.  If we outnumber, we win
        if (we > enemies.length) {

            // Must attack nearest
            MapLocation nearest = _getNearestAttacker(rc, nmeLocs);
            if (nearest == null) {
                for (int i = 0; i < enemies.length; i++) {
                    if (nmeInfos[i].type == RobotType.HQ) {
                        continue;
                    }
                    if (_combatAttack(rc, currentLoc, nmeLocs[i])) {
                        return;
                    }
                }
            } else {
                rc.attackSquare(nearest);
                return;
            }
        }

        // TODO: This later
//        // Don't be the first person to attack
//        else if (we == enemies.length) {
//
//        }

        // Retreat?  Or blaze of glory?
        else {
            for (int i = 0; i < enemies.length; i++) {
                if (nmeInfos[i].type == RobotType.HQ) {
                    continue;
                }
                if (rc.canAttackSquare(nmeLocs[i])) {
                    System.out.println("Attacking: " + nmeLocs[i]);
                    rc.attackSquare(nmeLocs[i]);
                    return;
                }
            }
            // If none are in range, get closer to weakest
            _combatMove(rc, currentLoc, nmeLocs[0]);
        }
    }

    private static boolean _combatAttack(RobotController rc, MapLocation loc, MapLocation enemyLoc) throws GameActionException {
        if (!rc.canAttackSquare(enemyLoc)) {
            return _combatMove(rc, loc, enemyLoc);
        }

        rc.attackSquare(enemyLoc);
        return true;
    }

    // Validates if there are any nearest attackers.  Always go for the nearest attackers first.
    // TODO: *NOTE* enemies is already sorted lowest HP
    private static MapLocation _getNearestAttacker(RobotController rc, MapLocation[] enemies) {
        for (int i = 0; i < enemies.length; i++) {
            if (rc.canAttackSquare(enemies[i])) {
                return enemies[i];
            }
        }

        return null;
    }

    private static boolean _combatMove(RobotController rc, MapLocation from, MapLocation to) throws GameActionException {
        Direction dirTo = from.directionTo(to);
        if (rc.canMove(dirTo)) {
            rc.move(dirTo);
            return true;
        }

        Direction leftTo = dirTo;
        Direction rightTo = dirTo;

        // Will search the entire space.
        for (int i = 0; i < 4; i++) {
            leftTo = leftTo.rotateLeft();
            if (rc.canMove(leftTo)) {
                rc.move(leftTo);
                return true;
            }

            rightTo = rightTo.rotateRight();
            if (rc.canMove(rightTo)) {
                rc.move(rightTo);
                return true;
            }
        }

        // Cannot move
        return false;
    }

    // TODO: MUTATES!  Ohh yeah, go java destructuring!
    private static void _fillData(RobotController rc, Robot[] enemies, RobotInfo[] infos, MapLocation[] locs, int[] hps) throws GameActionException {
        int len = infos.length;

        for (int i = 0; i < len; i++) {
            RobotInfo info = rc.senseRobotInfo(enemies[i]);
            infos[i] = info;
            locs[i] = infos[i].location;
            hps[i] = (int)infos[i].health;
        }
    }

    private static void _sort(Robot[] enemies, MapLocation[] enemyLocs, int[] enemyHps) {
        int currentHp;
        MapLocation currentLoc;
        Robot currentEnemy;
        for (int i = 1, j, len = enemyLocs.length; i < len; i++) {
            currentHp = enemyHps[i];
            currentLoc = enemyLocs[i];
            currentEnemy = enemies[i];

            for (j = i; j > 0 && currentHp < enemyHps[j - 1]; j--) {
                enemyLocs[j] = enemyLocs[j - 1];
                enemyHps[j] = enemyHps[j - 1];
                enemies[j] = enemies[j - 1];
            }

            enemyHps[j] = currentHp;
            enemyLocs[j] = currentLoc;
            enemies[j] = currentEnemy;
        }
    }

    private static void _init(RobotController rc) {
        attackRadius = RobotType.SOLDIER.sensorRadiusSquared;
        halfRange = (int)Math.sqrt(attackRadius);
        range = halfRange * 2;
        init = true;
    }
}

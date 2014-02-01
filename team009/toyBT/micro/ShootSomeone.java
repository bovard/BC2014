package team009.toyBT.micro;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;
import team009.utils.SmartMapLocationArray;
import team009.utils.SmartRobotInfoArray;

public class ShootSomeone extends Behavior {
    private ToySoldier soldier;
    private int hqMaxDistance = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared + 1) + 1, 2) - 1;
    private int sensorRadius, attackRadius, oneAwayAttackRadius, halfRange, range;
    public ShootSomeone(ToySoldier robot) {
        super(robot);
        soldier = robot;
        sensorRadius = RobotType.SOLDIER.sensorRadiusSquared;
        attackRadius = RobotType.SOLDIER.attackRadiusMaxSquared;
        oneAwayAttackRadius = (int)Math.sqrt(attackRadius) + 1;
        oneAwayAttackRadius *= oneAwayAttackRadius;
        halfRange = (int)Math.sqrt(attackRadius);
        range = halfRange * 2;
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).engagedInCombat;
    }

    @Override
    public boolean run() throws GameActionException {
        soldier.rc.setIndicatorString(2, "Shooting someone");
        SmartRobotInfoArray nmeInfos = soldier.enemySoldiers;
        MapLocation[] nmeLocs = new MapLocation[soldier.enemySoldiers.length];
        int[] nmeHps = new int[soldier.enemySoldiers.length];

        _fillData(nmeInfos, nmeLocs, nmeHps);
        _sort(nmeInfos, nmeLocs, nmeHps);
        SmartMapLocationArray currentAttackableEnemies = new SmartMapLocationArray();
        for (RobotInfo info: soldier.enemySoldiersInRange.arr) {
            currentAttackableEnemies.add(info.location);
        }
        MapLocation nearestEnemy = soldier.nearestEnemy.location;

        MapLocation target = nearestEnemy == null ? nmeLocs[0] : nearestEnemy;

        if (robot.rc.canAttackSquare(target)) {
            robot.rc.attackSquare(target);
            return true;
        }
        System.out.println("something weird happened in Engage");
        return true;
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

    private void _fillData(SmartRobotInfoArray infos, MapLocation[] locs, int[] hps) throws GameActionException {
        int len = infos.length;

        for (int i = 0; i < len; i++) {
            locs[i] = infos.arr[i].location;
            hps[i] = (int)infos.arr[i].health;
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


}

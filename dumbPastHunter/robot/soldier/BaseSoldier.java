package dumbPastHunter.robot.soldier;

import battlecode.common.*;
import dumbPastHunter.RobotInformation;
import dumbPastHunter.robot.TeamRobot;

public abstract class BaseSoldier extends TeamRobot {
    // -----------------------------------------------------
    // Commands
    // -----------------------------------------------------
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];
    public int group;
    public int type;

    public BaseSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = false;
        if (enemies.length > 0) {
            seesEnemy = true;
            if (enemies.length == 1 && rc.senseRobotInfo(enemies[0]).type == RobotType.HQ) {
                seesEnemy = false;
                enemies = new Robot[0];
            }
        }

    }

    // Somtimes i wish valid identifiers could contain explanation points!
    public static final int ATTACK = 2;
}

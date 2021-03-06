package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.communication.decoders.GroupCommandDecoder;
import team009.robot.TeamRobot;

public abstract class BaseSoldier extends TeamRobot {
    // -----------------------------------------------------
    // Commands
    // -----------------------------------------------------
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];
    public double health;
    public GroupCommandDecoder groupCommand;
    public GroupCommandDecoder hqCommand;
    public RobotInfo firstEnemy;

    public BaseSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
        currentLoc = rc.getLocation();
        lastLoc = info.hq;
        health = rc.getHealth();
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        MapLocation temp = rc.getLocation();
        if (!temp.equals(currentLoc)) {
            lastLoc = currentLoc;
        }
        currentLoc = temp;
        health = rc.getHealth();
        seesEnemy = false;
        RobotInfo firstNonHQEnemy = null;

        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = false;
        if (enemies.length > 0) {
            seesEnemy = enemies.length > 0;
            firstEnemy = getFirstNonHQRobot(enemies);
            if (firstEnemy == null) {
                seesEnemy = false;
                enemies = new Robot[0];
            }
        }
    }

    private RobotInfo getFirstNonHQRobot(Robot[] robots) throws GameActionException {
        for (Robot r : robots) {
            RobotInfo info = rc.senseRobotInfo(r);

            if (info.type != RobotType.HQ) {
                return info;
            }
        }

        return null;
    }

}

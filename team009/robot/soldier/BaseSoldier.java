package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.robot.TeamRobot;

public abstract class BaseSoldier extends TeamRobot {
    public static final int DEFENDER_GROUP = 1;
    // -----------------------------------------------------
    // Commands
    // -----------------------------------------------------
    public boolean seesEnemy = false;
    public double health;
    public Robot[] enemies = new Robot[0];
    public int group;
    public int type;
    public GroupCommandDecoder decoder;
    public RobotInfo firstEnemy;

    public BaseSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        health = rc.getHealth();
        seesEnemy = false;
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        //TODO figure out if the above call is needed ^^ seem like a dup

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

    // Somtimes i wish valid identifiers could contain explanation points!
    public static final int DEFEND = 2;
    public static final int ATTACK = 3;
}

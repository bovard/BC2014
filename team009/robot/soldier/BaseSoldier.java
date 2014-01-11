package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.robot.TeamRobot;

public abstract class BaseSoldier extends TeamRobot {
    // -----------------------------------------------------
    // Commands
    // -----------------------------------------------------
    public boolean seesEnemy = false;
    public double health;
    public Robot[] enemies = new Robot[0];
    public int group;
    public int type;
    public GroupCommandDecoder decoder;

    public BaseSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        health = rc.getHealth();
        seesEnemy = false;
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        RobotInfo firstNonHQEnemy = null;

        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = false;
        if (enemies.length > 0) {
            seesEnemy = enemies.length > 0;
            firstNonHQEnemy = getFirstNonHQRobot(enemies);
            if (firstNonHQEnemy == null) {
                seesEnemy = false;
                enemies = new Robot[0];
            }
        }

        // writes out any information about its environment.
        if (Communicator.WriteRound(round)) {
            Communicator.WriteTypeAndGroup(rc, type, group);

            // If there is no decoder or no data, then write out information about the environment.
            String str = "Decoder: " + decoder;
            if (decoder == null || !decoder.hasData()) {

                if (seesEnemy) {
                    Communicator.WriteToGroup(rc, group, BaseSoldier.ATTACK, firstNonHQEnemy.location);
                    str += " At " + firstNonHQEnemy.location;
                }
            }
            rc.setIndicatorString(0, str);
        }

        // Updates the decoder with any information.
        if (Communicator.ReadRound(round)) {
            decoder = Communicator.ReadFromGroup(rc, group);
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
    public static final int ATTACK = 2;
}

package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.SoldierDecoder;

public class SoldierSpawner {

    public static int SOLDIER_TYPE_DUMB = 0;
    public static int SOLDIER_TYPE_PASTURE_CAPTURER = 1;
    public static int SOLDIER_TYPE_HERDER = 2;
    public static int SOLDIER_COUNT = 3;
    public static final int MAX_GROUP_COUNT = 4;

    public static BaseSoldier getSoldier(RobotController rc, RobotInformation info) {
        BaseSoldier robot = null;
        try {
            SoldierDecoder decoder = Communicator.ReadNewSoldier(rc);

            int type = decoder.soldierType;
            System.out.println("type: " + type);
            System.out.println("group: " + decoder.group);
            System.out.println("location: " + decoder.loc);
            if (type == SOLDIER_TYPE_DUMB) {
                robot = new DumbSoldier(rc, info);
            } else if (type == SOLDIER_TYPE_PASTURE_CAPTURER) {
                robot = new PastureCapture(rc, info, decoder.loc);
            } else if (type == SOLDIER_TYPE_HERDER) {
                robot = new Herder(rc, info, decoder.loc);
            } else {
                System.out.println("Didn't find a valid robot!");
            }

            robot.group = decoder.group;
            robot.type = type;
        } catch (GameActionException e) {
            e.printStackTrace();
        }
        return robot;
    }


    // -----------------------------------------------------
    // Commands
    // -----------------------------------------------------
    public static final int RETREAT_TO_PASTURE = 1;
}

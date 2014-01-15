package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.SoldierDecoder;

public class SoldierSpawner {

    public static final int SOLDIER_TYPE_DUMB = 0;
    public static final int SOLDIER_TYPE_PASTURE_CAPTURER = 1;
    public static final int SOLDIER_TYPE_HERDER = 2;
    public static final int SOLDIER_TYPE_SOUND_TOWER = 3;
    public static final int DUMB_PASTR_HUNTER = 4;
    public static final int SOLDIER_TYPE_WOLF = 5;
    public static final int SOLDIER_TYPE_BACKDOOR_NOISE_PLANTER = 6;
    public static final int SOLDIER_TYPE_JACKAL = 7;
    public static final int SOLDIER_COUNT = 8;


    public static final int MAX_GROUP_COUNT = 5;

    public static BaseSoldier getSoldier(RobotController rc, RobotInformation info) {
        BaseSoldier robot = null;
        try {
            SoldierDecoder decoder = Communicator.ReadNewSoldier(rc);
            System.out.println("New Soldier: " + decoder.toString());

            int type = decoder.soldierType;
            switch (type) {
                case SOLDIER_TYPE_SOUND_TOWER:
                    System.out.println("making new sound tower");
                    robot = new SoundTowerCapture(rc, info, decoder.loc);
                    break;
                case DUMB_PASTR_HUNTER:
                    System.out.println("making new dumb herder");
                    robot = new DumbPastrHunter(rc, info);
                    break;
                case SOLDIER_TYPE_HERDER:
                    System.out.println("making new herder");
                    robot = new Herder(rc, info, decoder.loc);
                    break;
                case SOLDIER_TYPE_PASTURE_CAPTURER:
                    System.out.println("making new pasture capture");
                    robot = new PastureCapture(rc, info, decoder.loc);
                    break;
                case SOLDIER_TYPE_WOLF:
                    System.out.println("making new wolf");
                    robot = new Wolf(rc, info);
                    break;
                case SOLDIER_TYPE_BACKDOOR_NOISE_PLANTER:
                    System.out.println("making new noise planter");
                    robot = new BackdoorNoisePlanter(rc, info);
                    break;
                case SOLDIER_TYPE_JACKAL:
                    System.out.println("making new jackal");
                    robot = new Jackal(rc, info);
                    break;
                case SOLDIER_TYPE_DUMB:
                default:
                    System.out.println("making new dumb soldier");
                    robot = new DumbSoldier(rc, info);
                    break;
            }

            robot.group = decoder.group;
            robot.type = type;
        } catch (GameActionException e) {
            e.printStackTrace();
        }
        return robot;
    }


}

package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;

public class HQSpawner {

    public static final int DEFENSIVE_PASTURE = 0;
    public static final int OFFENSIVE = 1;
    public static final int BALANCED = 2;
    public static final int DUMB_PASTR_HUNT = 3;
    public static final int BACK_DOOR_TEST = 4;
    public static final int SPRINT = 5;
    public static final int NOISE_TEST = 6;
    public static final int COM_TEST = 7;
    public static final int MICRO_TEST = 8;
    public static final int HQ_TEST = 9;
    public static final int DARK_HORSE = 10;

    public static HQ getHQ(RobotController rc, RobotInformation info) {
        HQ robot = null;

        int type;
        // TODO: use this
        // type = TeamMemoryManager.getHQStrategy();
//        type = SPRINT;
//        type = MICRO_TEST;
        //type = BACK_DOOR_TEST;
        //type = DEFENSIVE_PASTURE;
        //type = NOISE_TEST;
        type = OFFENSIVE;

        switch(type) {
            case DEFENSIVE_PASTURE:
                robot = new Defensive(rc, info);
                break;
            case OFFENSIVE:
                robot = new Offensive(rc, info);
                break;
            case BALANCED:
                //robot =
                break;
            case DUMB_PASTR_HUNT:
                robot = new DumbPastrHunter(rc, info);
                break;
            case BACK_DOOR_TEST:
                robot = new BackDoorTest(rc, info);
                break;
            case SPRINT:
                robot = new Sprint(rc, info);
                break;
            case NOISE_TEST:
                robot = new NoiseTest(rc, info);
                break;
            case COM_TEST:
                robot = new ComTest(rc, info);
                break;
            case MICRO_TEST:
                robot = new MicroTest(rc, info);
                break;
            case HQ_TEST:
                robot = new HQTest(rc, info);
                break;
            case DARK_HORSE:
                robot = new DarkHorse(rc, info);
                break;
            default:
                robot = new Offensive(rc, info);
                break;
        }
        return robot;
    }
}

package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.communication.TeamMemoryManager;

public class HQSpawner {

    public static final int DEFENSIVE_PASTURE = 0;
    public static final int PASTURE_HUNTING = 1;
    public static final int BALANCED = 2;
    public static final int DUMB_PASTR_HUNT = 3;
    public static final int BACK_DOOR_TEST = 4;
    public static final int SPRINT = 5;
    public static final int NOISE_TEST = 6;

    public static HQ getHQ(RobotController rc, RobotInformation info) {
        HQ robot = null;

        int type;
        // TODO: use this
        // type = TeamMemoryManager.getHQStrategy();
        type = SPRINT;
        //type = BACK_DOOR_TEST;
        //type = DEFENSIVE_PASTURE;
        //type = NOISE_TEST;

        switch(type) {
            case DEFENSIVE_PASTURE:
                robot = new Defensive(rc, info);
                break;
            case PASTURE_HUNTING:
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
            default:
                robot = new Offensive(rc, info);
                break;
        }
        return robot;
    }
}

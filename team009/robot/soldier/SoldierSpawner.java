package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.decoders.SoldierDecoder;
import team009.robot.TeamRobot;

public class SoldierSpawner {

    public static final int SOLDIER_TYPE_DUMB = 0;
    public static final int SOLDIER_TYPE_PASTURE_CAPTURER = 1;
    public static final int SOLDIER_TYPE_HERDER = 2;
    public static final int SOLDIER_TYPE_SOUND_TOWER = 3;
    public static final int DUMB_PASTR_HUNTER = 4;
    public static final int SOLDIER_TYPE_WOLF = 5;
    public static final int SOLDIER_TYPE_BACKDOOR_NOISE_PLANTER = 6;
    public static final int SOLDIER_TYPE_JACKAL = 7;
    public static final int SOLDIER_TYPE_DEFENDER = 8;
    public static final int SOLDIER_TYPE_TOY_SOLDIER = 9;
    public static final int SOLDIER_COUNT = 10;


    public static TeamRobot getSoldier(RobotController rc, RobotInformation info) {
        TeamRobot robot = null;
        try {
            SoldierDecoder decoder = Communicator.ReadNewSoldier(rc);

            int type = decoder.soldierType;
            switch (type) {
                case SOLDIER_TYPE_SOUND_TOWER:
                    //System.out.println("making new sound tower");
                    robot = new SoundTowerCapture(rc, info, decoder.loc);
                    break;
                case DUMB_PASTR_HUNTER:
                    //System.out.println("making new dumb herder");
                    robot = new DumbPastrHunter(rc, info);
                    break;
                case SOLDIER_TYPE_HERDER:
                    //System.out.println("making new herder");
                    robot = new Herder(rc, info, decoder.loc);
                    break;
                case SOLDIER_TYPE_PASTURE_CAPTURER:
                    //System.out.println("making new pasture capture");
                    robot = new PastureCapture(rc, info, decoder.loc);
                    break;
                case SOLDIER_TYPE_WOLF:
                    //System.out.println("making new wolf");
                    robot = new Wolf(rc, info);
                    break;
                case SOLDIER_TYPE_BACKDOOR_NOISE_PLANTER:
                    //System.out.println("making new noise planter");
                    robot = new BackdoorNoisePlanter(rc, info);
                    break;
                case SOLDIER_TYPE_JACKAL:
                    //System.out.println("making new jackal");
                    robot = new Jackal(rc, info);
                    break;
                case SOLDIER_TYPE_DEFENDER:
                case SOLDIER_TYPE_DUMB:
                    robot = new Defender(rc, info);
                    break;
                case SOLDIER_TYPE_TOY_SOLDIER:
                default:
                    robot = new ToySoldier(rc, info);
                    break;
            }

            robot.group = decoder.group;
            robot.type = type;
            robot.twoWayChannel = decoder.comChannel;
        } catch (GameActionException e) {
            e.printStackTrace();
        }
        return robot;
    }


}

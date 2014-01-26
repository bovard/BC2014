package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.decoders.SoldierDecoder;
import team009.robot.TeamRobot;

public class SoldierSpawner {

    public static TeamRobot getSoldier(RobotController rc, RobotInformation info) {
        TeamRobot robot = null;
        try {
            SoldierDecoder decoder = Communicator.ReadNewSoldier(rc);

            int type = decoder.soldierType;
            switch (type) {
                case TeamRobot.SOLDIER_TYPE_TOY_SOLDIER:
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

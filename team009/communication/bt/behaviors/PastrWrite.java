package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.robot.Pastr;
import team009.robot.TeamRobot;

public class PastrWrite extends WriteBehavior {

    public PastrWrite(Pastr pastr) {
        super(pastr);
    }

    public boolean run() throws GameActionException {
        Communicator.WriteTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_PASTR, TeamRobot.PASTR_GROUP, robot.currentLoc);
        Communicator.WriteTwoWayCommunicate(rc, robot.twoWayChannel, TeamRobot.POSITION_OF_PASTR, robot.currentLoc, new MapLocation(0, 0));

        // TODO: If there is an enemy nearby then communicate to team that i am being attacked
        return true;
    }
}


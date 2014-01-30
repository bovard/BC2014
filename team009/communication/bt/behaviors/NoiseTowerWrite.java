package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.robot.NoiseTower;
import team009.robot.TeamRobot;

public class NoiseTowerWrite extends WriteBehavior {

    public NoiseTowerWrite(NoiseTower tower) {
        super(tower);
    }

    public boolean run() throws GameActionException {
        Communicator.WriteTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_NOISE_TOWER, TeamRobot.NOISE_TOWER_GROUP, robot.currentLoc);
        Communicator.WriteTwoWayCommunicate(rc, robot.twoWayChannel, TeamRobot.POSITION_OF_NOISE_TOWER, robot.currentLoc, new MapLocation(0, 0));

        // TODO: If there is an enemy nearby then communicate to team that i am being attacked
        return true;
    }
}


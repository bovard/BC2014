package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.TeamRobot;
import team009.robot.soldier.SoldierSpawner;

public class HQClearStateCom extends Behavior {
    public HQClearStateCom(TeamRobot robot) {
        super(robot);
    }

    public boolean pre() throws GameActionException {
        // NOTE: This is not decided here
        return true;
    }

    public boolean run() throws GameActionException {

        int type = SoldierSpawner.SOLDIER_TYPE_TOY_SOLDIER;
        int groups = Communicator.MAX_GROUP_COUNT;
        for (int i = 0; i < groups; i++) {
            Communicator.ClearCountChannel(rc, type, i);
        }
        return true;
    }
}


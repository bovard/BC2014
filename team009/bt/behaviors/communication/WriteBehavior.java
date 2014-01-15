package team009.bt.behaviors.communication;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.TeamRobot;

public abstract class WriteBehavior extends Behavior {
    public WriteBehavior(TeamRobot robot) {
        super(robot);
    }

    public boolean pre() throws GameActionException {
        return Communicator.WriteRound(robot.round);
    }
}


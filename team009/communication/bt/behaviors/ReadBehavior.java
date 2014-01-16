package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.TeamRobot;

public abstract class ReadBehavior extends Behavior {
    public ReadBehavior(TeamRobot robot) {
        super(robot);
    }

    public boolean pre() throws GameActionException {
        return Communicator.ReadRound(robot.round);
    }
}

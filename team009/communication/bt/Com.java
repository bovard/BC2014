package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.bt.decisions.Decision;
import team009.communication.Communicator;
import team009.robot.TeamRobot;

public class Com extends Decision {
    Behavior write;
    Behavior read;

    public Com(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {
        if (robot.round % Communicator.INFORMATION_ROUND_MOD == 0) {
            write.run();
        } else if (robot.round % Communicator.INFORMATION_ROUND_MOD == 1) {
            read.run();
        }
        return true;
    }
}


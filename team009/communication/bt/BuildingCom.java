package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.communication.bt.behaviors.NoiseTowerWrite;
import team009.communication.bt.behaviors.PastrWrite;
import team009.robot.TeamRobot;

public class BuildingCom extends Com {
    public BuildingCom(TeamRobot robot, NoiseTowerWrite write) {
        super(robot);
        this.write = write;
    }

    public BuildingCom(TeamRobot robot, PastrWrite write) {
        super(robot);
        this.write = write;
    }

    public boolean run() throws GameActionException {
        if (robot.round % Communicator.INFORMATION_ROUND_MOD == 0) {
            write.run();
        }
        return true;
    }
}

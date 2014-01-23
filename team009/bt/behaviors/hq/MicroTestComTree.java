package team009.bt.behaviors.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class MicroTestComTree extends WriteBehavior {
    private int lastCommand = 0;
    private int idx = 0;
    private HQ hq;
    private int group = 0;
    public MicroTestComTree(HQ hq) {
        super(hq);
        this.hq = hq;
    }

    public boolean run() throws GameActionException {

        if (Clock.getRoundNum() - lastCommand > 250 && hq.comDefend(robot.info.center, group, 75)) {
            lastCommand = Clock.getRoundNum();
            idx = (idx + 1) % 4;
            group = (group + 1) % Communicator.MAX_GROUP_COUNT;
        }
        return true;
    }
}

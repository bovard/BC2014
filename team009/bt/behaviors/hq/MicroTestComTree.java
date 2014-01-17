package team009.bt.behaviors.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class MicroTestComTree extends WriteBehavior {
    private int lastCommand = -1000;
    private int idx = 0;
    private HQ hq;
    public MicroTestComTree(HQ hq) {
        super(hq);
        this.hq = hq;
    }

    public boolean run() throws GameActionException {

        if (Clock.getRoundNum() - lastCommand > 150 && hq.comDefend(robot.info.center, TeamRobot.TOY_GROUP)) {
            lastCommand = Clock.getRoundNum();
            idx = (idx + 1) % 4;
        }
        return true;
    }
}

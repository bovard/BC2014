package team009.bt.behaviors.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class ComTestComTree extends WriteBehavior {
    private int lastCommand = -1000;
    private int idx = 0;
    private MapLocation[] locs = new MapLocation[4];
    private HQ hq;
    public ComTestComTree(HQ hq) {
        super(hq);
        this.hq = hq;
        locs[0] = new MapLocation(5, 5);
        locs[1] = new MapLocation(robot.info.width - 6, 5);
        locs[2] = new MapLocation(robot.info.width - 6, robot.info.height - 6);
        locs[3] = new MapLocation(5, robot.info.height - 6);
    }

    public boolean run() throws GameActionException {

        System.out.println("ComTree: " + lastCommand);
        if (Clock.getRoundNum() - lastCommand > 150 && hq.comDefend(locs[idx], TeamRobot.DEFENDER_GROUP)) {
            lastCommand = Clock.getRoundNum();
            System.out.println("Communicated!!");
            System.out.println("Communicated!!");
            System.out.println("Communicated!!");
            System.out.println("Communicated!!");
            System.out.println("Communicated!!");
            System.out.println("Communicated!!");
            idx = (idx + 1) % 4;
        }
        return true;
    }
}

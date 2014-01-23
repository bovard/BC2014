package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
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

//        if (Clock.getRoundNum() - lastCommand > 150 && hq.comDefend(locs[idx], 0)) {
//            lastCommand = Clock.getRoundNum();
//            idx = (idx + 1) % 4;
//        }
        return true;
    }
}

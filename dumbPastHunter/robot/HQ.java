package dumbPastHunter.robot;

import battlecode.common.*;
import dumbPastHunter.MapUtils;
import dumbPastHunter.RobotInformation;
import dumbPastHunter.bt.Node;
import dumbPastHunter.bt.behaviors.hq.DumbPastrHunter;

public class HQ extends TeamRobot {

    // This will adjust to how many soldiers we have.
    private int maxSoldiers;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new DumbPastrHunter(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();


    }

    private Direction _getSpawnDirection() {
        Direction dir = null;
        boolean done = false;
        int tries = 0;
        while(!done && tries < 20) {
            tries++;
            dir = MapUtils.getRandomDir();
            if (rc.canMove(dir)) {
                done = true;
            }
        }

        return done ? dir : null;
    }
}

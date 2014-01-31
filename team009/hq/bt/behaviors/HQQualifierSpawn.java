package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.BehaviorConstants;
import team009.bt.behaviors.Behavior;
import team009.hq.robot.Qualifier;

public class HQQualifierSpawn extends Behavior {
    private Qualifier q;

    public HQQualifierSpawn(Qualifier robot) {
        super(robot);
        q = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        // no preconditions
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {

        //robot count
        int robotCount = robot.rc.senseRobotCount();

        // spawn guys
        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            // q.soldierCountsZero is only group 0
//            if (q.soldierCountsZero.count < BehaviorConstants.GROUP_0_SIZE_SMALL_MAP) {
                q.createToySoldier(0);
//            } else {
//                q.createToySoldier(1);
//            }
        }

        return true;
    }

}


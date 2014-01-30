package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import team009.bt.behaviors.Behavior;
import team009.hq.robot.Qualifier;

public class HQQualifierSpawn extends Behavior {
    private Qualifier q;
    private int spawned = 0;

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
        if (spawned == 0 && robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            q.createToySoldier(q.groupToSpawn);
            return true;
        }
        spawned++;

        return true;
    }

}


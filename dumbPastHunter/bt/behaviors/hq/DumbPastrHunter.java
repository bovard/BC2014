package dumbPastHunter.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import dumbPastHunter.MapUtils;
import dumbPastHunter.bt.behaviors.Behavior;
import dumbPastHunter.robot.HQ;
import dumbPastHunter.robot.TeamRobot;

public class DumbPastrHunter extends Behavior {
    public DumbPastrHunter(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        if (robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            Direction dir = MapUtils.getRandomDir();
            int count = 0;
            while (!robot.rc.canMove(dir) && count < 9) {
                count++;
                dir = dir.rotateLeft();
            }
            robot.rc.spawn(dir);
        }
        return true;
    }
}

package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;

public class HealNearHQ extends Behavior {
    private BugMove move;

    public HealNearHQ(TeamRobot robot) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(robot.info.hq);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
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
        int distToHQ = robot.currentLoc.distanceSquaredTo(robot.info.hq);
        if (distToHQ > 36 && distToHQ > 16) {
            move.move();
        }
        return true;
    }
}

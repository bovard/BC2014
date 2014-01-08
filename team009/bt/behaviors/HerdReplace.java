package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.navigation.BasicMove;
import team009.robot.TeamRobot;

public class HerdReplace extends Behavior {
    protected MapLocation pastureLocation;
    protected BasicMove move;

    public HerdReplace(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        this.move = new BasicMove(robot);
        move.setDestination(pastureLocation);
    }

    @Override
    public boolean pre() throws GameActionException {
        // we can sense the square and we see that there isn't a pastr there!
        return robot.rc.canSenseSquare(pastureLocation) && (robot.rc.senseObjectAtLocation(pastureLocation) == null || robot.currentLoc.equals(pastureLocation));
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
        if (robot.currentLoc.equals(pastureLocation)) {
            robot.rc.construct(RobotType.PASTR);
        } else {
            move.move();
        }
        return true;
    }
}

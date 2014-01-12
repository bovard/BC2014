package flow.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import flow.navigation.BugMove;
import flow.robot.soldier.Wolf;

public class WolfPastrHunt extends Behavior {

    protected MapLocation pasture;
    protected BugMove move;

    public WolfPastrHunt(Wolf robot) {
        super(robot);
        pasture = null;
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((Wolf)robot).pastrsExists;
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
        if(pasture == null ||
                (robot.rc.canSenseSquare(pasture)) && robot.rc.senseObjectAtLocation(pasture) != null && robot.rc.senseObjectAtLocation(pasture).getTeam() == robot.info.myTeam) {
            int pastrIndex = ((int)(Math.random()*((Wolf)robot).pastrLocs.length));
            pasture = ((Wolf)robot).pastrLocs[pastrIndex];
            move.setDestination(pasture);
        }
        // we are coming to kill all the pastures!
        robot.rc.broadcast(1234, 1234);
        move.move();
        return true;
    }
}

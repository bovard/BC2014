package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.navigation.BugMove;
import team009.robot.soldier.DumbPastrHunter;

public class DumbPastrHunt extends Behavior {

    protected MapLocation pasture;
    protected BugMove move;

    public DumbPastrHunt(DumbPastrHunter robot) {
        super(robot);
        pasture = null;
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((DumbPastrHunter)robot).pastrExists;
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
            pasture = ((DumbPastrHunter)robot).pastrLocations[0];
            move.setDestination(pasture);
        }
        // we are coming to kill all the pastures!
        robot.rc.broadcast(1234, 1234);
        move.move();
        return true;
    }
}

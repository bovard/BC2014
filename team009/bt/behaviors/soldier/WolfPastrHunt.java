package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.Wolf;

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
            int pastrIndex = 0;
            pasture = ((Wolf)robot).pastrLocs[pastrIndex];
            move.setDestination(pasture);
        }
        // we are coming to kill all the pastures!
        robot.rc.broadcast(1234, 1234);
        move.move();
        return true;
    }
}

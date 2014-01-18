package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class ToyHerdReplace extends Behavior {
    protected BugMove move;
    protected MapLocation pastureLocation;
    protected ToySoldier soldier;

    public ToyHerdReplace(ToySoldier robot) {
        super(robot);
        soldier = robot;
        pastureLocation = new MapLocation(0, 0);
        move = new BugMove(robot);
        move.setDestination(pastureLocation);
    }

    @Override
    public boolean pre() throws GameActionException {
        // we can sense the square and we see that there isn't a pastr there!
        return robot.rc.canSenseSquare(soldier.comLocation) && (robot.rc.senseObjectAtLocation(soldier.comLocation) == null || robot.currentLoc.equals(soldier.comLocation));
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
        if (robot.currentLoc.equals(soldier.comLocation)) {
            robot.rc.construct(RobotType.PASTR);
        } else {
            move.move();
        }
        return true;
    }
}

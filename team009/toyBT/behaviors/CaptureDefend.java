package team009.toyBT.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class CaptureDefend extends ToyMoveToLocation {
    boolean pastr, sound;
    public CaptureDefend(ToySoldier soldier) {
        super(soldier);
    }

    @Override
    public boolean pre() throws GameActionException {
        pastr = soldier.comCommand == TeamRobot.CAPTURE_PASTURE;
        sound = soldier.comCommand == TeamRobot.CAPTURE_SOUND;
        //rc.setIndicatorString(2, soldier.round + ": " + pastr + " : " + sound);
        return pastr || sound;
    }

    @Override
    public boolean run() throws GameActionException {
        update();

        // If we are adjacent, then continue moving if available.
        if (currentLocation.isAdjacentTo(soldier.currentLoc)) {
            if (rc.senseObjectAtLocation(currentLocation) == null) {
                super.run();
            }
            return true;
        }

        // If we are equal, then construct
        if (currentLocation.equals(soldier.currentLoc)) {
            if (pastr) {
                rc.construct(RobotType.PASTR);
            } else if (sound) {
                rc.construct(RobotType.NOISETOWER);
            }
            return true;
        }

        MapLocation adjacent = robot.currentLoc.add(robot.currentLoc.directionTo(currentLocation));
        if (adjacent.isAdjacentTo(currentLocation)) {
           if (robot.rc.senseObjectAtLocation(adjacent) == null) {
               super.run();

           }
           return true;
        }

        return true;
    }
}

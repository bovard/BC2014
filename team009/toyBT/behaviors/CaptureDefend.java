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
        return (pastr = soldier.comCommand == TeamRobot.CAPTURE_PASTURE) || (sound = soldier.comCommand == TeamRobot.CAPTURE_SOUND);
    }

    @Override
    public boolean run() throws GameActionException {
        update();

        // If we are adjacent, then continue moving if available.
        if (currentLocation.isAdjacentTo(soldier.currentLoc)) {
            if (rc.senseObjectAtLocation(currentLocation) == null) {
                super.run();
            }
        }

        // If we are equal, then construct
        else if (currentLocation.equals(soldier.currentLoc)) {
            if (pastr) {
                rc.construct(RobotType.PASTR);
            } else if (sound) {
                rc.construct(RobotType.NOISETOWER);
            }
        } else {
            Direction dir = soldier.currentLoc.directionTo(currentLocation);
            MapLocation loc = soldier.currentLoc.add(dir, 2);
            if (!loc.isAdjacentTo(currentLocation) && !loc.equals(currentLocation)) {
                super.run();
            }
        }

        return true;
    }
}

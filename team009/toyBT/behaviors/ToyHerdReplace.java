package team009.toyBT.behaviors;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class ToyHerdReplace extends Behavior {
    protected BugMove move;
    protected MapLocation pastureLocation;
    protected MapLocation soundLocation;
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
        boolean sensePasture, senseSound;
        Object atPasture, atSound;

        // we can sense the square and we see that there isn't a pastr there!
        if (!pastureLocation.equals(soldier.comLocation)) {
            pastureLocation = soldier.comLocation;
            soundLocation = getTowerLocation();
            move.setDestination(pastureLocation);
        }

        sensePasture = robot.rc.canSenseSquare(pastureLocation);
        senseSound = robot.rc.canSenseSquare(soundLocation);

        atPasture = sensePasture ? rc.senseObjectAtLocation(pastureLocation) : null;
        atSound = senseSound ? rc.senseObjectAtLocation(soundLocation) : null;

        return sensePasture && (atPasture == null || robot.currentLoc.equals(pastureLocation)) ||
               BehaviorConstants.NOISE_TOWER_ENABLE_WITH_PASTURE && senseSound && (atSound == null || robot.currentLoc.equals(soundLocation));
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
        } else if (robot.currentLoc.equals(soundLocation) && BehaviorConstants.NOISE_TOWER_ENABLE_WITH_PASTURE) {
            robot.rc.construct(RobotType.NOISETOWER);
        } else {
            move.move();
        }

        return true;
    }

    public MapLocation getTowerLocation()
    {
        //first check the spots next to the hq that are also next to the pasture (optimal spots)
        MapLocation tower = pastureLocation;
        Direction dir = Direction.NORTH;

        for (int i = 7; i >= 0; i--) {
            MapLocation curr = tower.add(dir);
            TerrainTile tile = rc.senseTerrainTile(curr);
            if (tile == TerrainTile.OFF_MAP || tile == TerrainTile.VOID) {
                dir = dir.rotateRight();
                continue;
            }

            return curr;
        }

        // Don't worry about it
        return null;
    }
}

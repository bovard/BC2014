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
        // we can sense the square and we see that there isn't a pastr there!
        if (!pastureLocation.equals(soldier.comLocation)) {
            pastureLocation = soldier.comLocation;
            //set ideal location of sound tower
            soundLocation = getTowerLocation();


            move.setDestination(pastureLocation);
        }
        return robot.rc.canSenseSquare(soldier.comLocation)
                && (robot.rc.senseObjectAtLocation(soldier.comLocation) == null
                    || robot.currentLoc.equals(soldier.comLocation));
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
            if (!pastureLocation.equals(soldier.comLocation)) {
                pastureLocation = soldier.comLocation;
                move.setDestination(pastureLocation);
            }
            move.move();
        }
        return true;
    }

    public MapLocation getTowerLocation()
    {
        //first check the spots next to the hq that are also next to the pasture (optimal spots)
        int hqx = hq.currentLoc.x;
        int hqy = hq.currentLoc.y;
        int px = pasture.x;
        int py = pasture.y;
        MapLocation[] optimalLocs = new MapLocation[4];

        if (hqx != px && hqy != py) {
            //only two optimal neighbors
            optimalLocs[0] = new MapLocation(hqx, py);
            optimalLocs[1] = new MapLocation(hqx, py);
        }
        else {
            //4 optimal neighbors
            if(hqx == px){
                optimalLocs[0] = new MapLocation(hqx-1, hqy);
                optimalLocs[1] = new MapLocation(hqx-1, py);
                optimalLocs[2] = new MapLocation(hqx+1, hqy);
                optimalLocs[3] = new MapLocation(hqx+1, py);
            }
            else {
                optimalLocs[0] = new MapLocation(hqx, hqy-1);
                optimalLocs[1] = new MapLocation(px, hqy+1);
                optimalLocs[2] = new MapLocation(hqx, hqy-1);
                optimalLocs[3] = new MapLocation(px, hqy-1);
            }
        }

        for(int i=0; i<optimalLocs.length; i++) {
            if(optimalLocs[i] != null && hq.rc.canMove(hq.currentLoc.directionTo(optimalLocs[i]))) {
                return optimalLocs[i];
            }
        }

        //otherwise get random move
        MapLocation tower = hq.currentLoc.add(hq.getRandomSpawnDirection());
        return tower;
    }
}

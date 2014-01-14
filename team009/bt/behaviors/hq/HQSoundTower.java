package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.hq.HQ;

public class HQSoundTower extends Behavior {
    private HQ hq;
    private int spawned = 0;
    private MapLocation pasture;

    public HQSoundTower(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return spawned < 2;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        if (spawned == 0) {
            pasture = hq.currentLoc.add(hq.getRandomSpawnDirection());
            hq.createSoundTower(0, getTowerMove());
            spawned++;
            return true;
        }
        else if (spawned == 1) {
            hq.createPastureCapturer(0, pasture);
            spawned++;
            return true;
        }
        return false;
    }

    public MapLocation getTowerMove()
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


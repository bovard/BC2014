package team009.communication.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.hq.robot.Qualifier;
import team009.utils.MapQuadrantUtils;

public class HQOneBase extends WriteBehavior {
    Qualifier hq;
    int group;

    private MapLocation pastrSpot;
    private MapLocation noiseSpot;
    private MapLocation corner;
    private double[][] cowGrowth;


    public HQOneBase(Qualifier off, int group) {
        super(off);
        hq = off;
        this.group = group;

        MapQuadrantUtils.hq = robot.info.hq;
        MapQuadrantUtils.enemyHq = robot.info.enemyHq;
        MapQuadrantUtils.width = robot.info.width;
        MapQuadrantUtils.height = robot.info.height;
        int ourQuad = MapQuadrantUtils.getMapQuadrant(robot.info.hq);
        int newQuad = ourQuad + 1;
        if (newQuad > 4) {
            newQuad = 1;
        }
        corner = MapQuadrantUtils.getMapCornerForQuadrant(newQuad);
        cowGrowth = robot.rc.senseCowGrowth();
        calcPastrSpot();
    }

    @Override
    public boolean run() throws GameActionException {
        MapLocation bestSpot = pastrSpot;
        MapLocation noiseLoc = noiseSpot;

        boolean hasSound = false;
        for (int i = 0; i < hq.noiseLocations.length; i++) {
            if (hq.noiseLocations.arr[i].equals(noiseLoc)) {
                hasSound = true;
                break;
            }
        }

        if (hasSound) {
            System.out.println("BestSpot: " + bestSpot);
            hq.comCapture(bestSpot, group);
        } else {
            System.out.println("Noise: " + noiseLoc);
            hq.comSoundTower(noiseLoc, group);
        }
        return true;
    }



    private void calcPastrSpot() {
        boolean done = false;
        pastrSpot = corner;
        int moveOutOfCorner = 7;
        // move a little bit out of the corner
        for (int i = 0; i < moveOutOfCorner; i++) {
            pastrSpot = pastrSpot.add(pastrSpot.directionTo(robot.info.center));
        }

        int maxTimes = Math.min(Math.abs(robot.info.center.x - corner.x), Math.abs(robot.info.center.y - corner.y));
        int count = moveOutOfCorner;
        // find a good spot
        while (!done && count < maxTimes) {
            if (robot.rc.senseTerrainTile(pastrSpot).equals(TerrainTile.NORMAL)
                    && cowGrowth[pastrSpot.x][pastrSpot.y] > 0) {
                if(robot.rc.senseTerrainTile(pastrSpot.add(pastrSpot.directionTo(robot.info.center))).equals(TerrainTile.NORMAL)) {
                    done = true;
                }

            }
            if (!done) {
                count++;
                pastrSpot = pastrSpot.add(pastrSpot.directionTo(robot.info.center));

            }
            if (done) {
                noiseSpot = pastrSpot.add(pastrSpot.directionTo(robot.info.center));

            }
        }

    }
}

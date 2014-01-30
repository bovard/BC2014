package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.bt.behaviors.Behavior;
import team009.hq.HQ;
import team009.utils.MapQuadrantUtils;

public class NoiseTester extends Behavior {

    private MapLocation pastrSpot;
    private MapLocation corner;
    private double[][] cowGrowth;

    public NoiseTester(HQ robot) {
        super(robot);
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
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (robot.rc.senseRobotCount() < 1) {
            ((HQ)robot).createPastureCapturer(0, pastrSpot);
        } else if (robot.rc.senseRobotCount() < 2) {
            ((HQ)robot).createSoundTower(0, pastrSpot.add(pastrSpot.directionTo(robot.info.center)));
        }
        return true;
    }


    private void calcPastrSpot() {
        boolean done = false;
        pastrSpot = corner;
        int moveOutOfCorner = 5;
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
        }

    }
}

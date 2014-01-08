package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import team009.robot.HQ;
import team009.utils.MapQuadrantUtils;

import java.util.ArrayList;

public class HQBalanced extends Behavior {
    private int last = 0;
    private static int MAX_COUNT = 4;
    private HQ hq;
    private MapLocation[] goodPastrLocs;
    public HQBalanced(HQ robot) {
        super(robot);
        hq = robot;
        goodPastrLocs = pasturePlacements();
    }

    @Override
    public boolean pre() throws GameActionException {
        // There are never any preconditions that mean we shouldn't enter this state
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // This state never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // There is no state here so nothing to reset!

    }

    @Override
    public boolean run() throws GameActionException {
        // Spawn a guy at a random location
        if (robot.rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            int group;
            MapLocation pasture;
            if (last % (goodPastrLocs.length * MAX_COUNT) < MAX_COUNT) {
                pasture = goodPastrLocs[0];
                group = 0;
            } else if (last % (goodPastrLocs.length * MAX_COUNT) < 2 * MAX_COUNT ) {
                pasture = goodPastrLocs[1];
                group = 1;
            } else if (last % (goodPastrLocs.length * MAX_COUNT) < 3 * MAX_COUNT) {
                pasture = goodPastrLocs[2];
                group = 2;
            } else {
                pasture = goodPastrLocs[3];
                group = 3;
            }
            last++;
            hq.createHerder(group, pasture);
        }
        return true;
    }


    private MapLocation[] pasturePlacements() {
        MapLocation[] possible = {
            new MapLocation(2, 2),
            new MapLocation(robot.info.width - 2, 2),
            new MapLocation(2, robot.info.height - 2),
            new MapLocation(robot.info.width - 2, robot.info.height - 2)
        };

        MapQuadrantUtils.setLocations(robot.info.hq, robot.info.enemyHq, robot.info.width, robot.info.height);

        ArrayList<MapLocation> locs = new ArrayList<MapLocation>();

        int hqQuad = MapQuadrantUtils.getMapQuadrant(robot.info.hq);
        int eHqQuad = MapQuadrantUtils.getMapQuadrant(robot.info.enemyHq);

        for (MapLocation p: possible) {
            int quad = MapQuadrantUtils.getMapQuadrant(p);
            if (quad != eHqQuad && quad != hqQuad) {
                locs.add(p);
            }
        }

        return locs.toArray(new MapLocation[locs.size()]);
    }
}

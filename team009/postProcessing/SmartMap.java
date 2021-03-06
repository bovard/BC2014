package team009.postProcessing;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class SmartMap extends BasePostProcessor {

    /**
     * Turns   into
     *  XXXX   XXXX
     *  X      XXX
     *  X      XX
     *  X      X
     *
     * and
     *  this into
     *  XXX   XXX
     *  X     XXX
     *  XXX   XXX
     * etc...
     *
     * so we can move around correctly
     * @param rc
     */
    public SmartMap(RobotController rc) {
        super(rc);
    }

    @Override
    public boolean calculate(int roundLimit) {
        return false;
    }

    /**
     * NOTE: you have to make sure canMove = false before calling this
     * @param loc the location to check
     * @return the indexes of the obstaclse that loc is part of
     */
    public int[] getObstacleIndexsForLocation(MapLocation loc) {
        int[] indices = new int[0];
        // Use utils.ArrayBuilder here to grow the array

        return indices;
    }

    public boolean canMove(MapLocation loc) {
        return false;
    }

}

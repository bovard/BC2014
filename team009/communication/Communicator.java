package team009.communication;

import battlecode.common.*;

public class Communicator {

    /**
     * Creates a communication for a robot soldier that will
     */
    public static void WriteNewSoldier(RobotController rc, int soldierType, MapLocation location) throws GameActionException {
        SoldierDecoder soldierDecoder = new SoldierDecoder(soldierType, location);
        rc.broadcast(NEW_SOLDIER_CHANNEL, soldierDecoder.getData());
    }

    /**
     * Reads in the communication for the new soldier
     */
    public static SoldierDecoder ReadNewSoldier(RobotController rc) throws GameActionException {
        return new SoldierDecoder(rc.readBroadcast(NEW_SOLDIER_CHANNEL));
    }

    /*****************************************************
     * Channel Constants
     *///*************************************************
    private static int NEW_SOLDIER_CHANNEL = 0;
}

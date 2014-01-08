package team009.communication;

import battlecode.common.*;
import team009.RobotInformation;

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

    /**
     * Writes out the soldier type to the proper com channel
     */
    public static void WriteTypeAndGroup(RobotController rc, int soldierType, int group) throws GameActionException {
        int channel = soldierType * RobotInformation.MAX_GROUP_COUNT + group;
        SoldierCountDecoder dec = ReadTypeAndGroup(rc, soldierType, group);

        // Incs the channel
        dec.count++;
        rc.broadcast(channel, dec.getData());
    }

    public static SoldierCountDecoder ReadTypeAndGroup(RobotController rc, int soldierType, int group) throws GameActionException {
        int channel = soldierType * RobotInformation.MAX_GROUP_COUNT + group;
        int data = rc.readBroadcast(channel);

        // No Coms yet on this channel
        if (data == 0) {
            return new SoldierCountDecoder(soldierType, group);
        }
        return new SoldierCountDecoder(data);
    }

    /*****************************************************
     * Channel Constants
     *///*************************************************
    private static int NEW_SOLDIER_CHANNEL = 0;

    // Channels 1 through (MAX_GROUP_COUNT * SOLDIER_COUNT) + 1 are reserved for soldier communication
    private static int SOLDIER_TYPE_CHANNEL_BASE = 1;
}

package team009.communication;

import battlecode.common.*;
import team009.RobotInformation;
import team009.robot.soldier.SoldierSpawner;

public class Communicator {

    //-----------------------------------------------------
    // WRITING
    //-----------------------------------------------------

    public static void WriteNewSoldier(RobotController rc, int soldierType, MapLocation location) throws GameActionException {
        SoldierDecoder decoder = new SoldierDecoder(soldierType, location);
        _Broadcast(rc, NEW_SOLDIER_CHANNEL, decoder);
    }

    public static void WriteToGroup(RobotController rc, int group, int command) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(group, command);
        _Broadcast(rc, GROUP_CHANNEL_BASE + group, decoder);
    }

    public static void WriteToGroup(RobotController rc, int group, int command, MapLocation location) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(group, command, location);
        _Broadcast(rc, GROUP_CHANNEL_BASE + group, decoder);
    }

    public static void WriteTypeAndGroup(RobotController rc, int soldierType, int group) throws GameActionException {
        int channel = soldierType * SoldierSpawner.MAX_GROUP_COUNT + group;
        SoldierCountDecoder decoder = ReadTypeAndGroup(rc, soldierType, group);

        // Incs the channel
        decoder.count++;
        _Broadcast(rc, channel, decoder);
    }

    //-----------------------------------------------------
    // READING
    //-----------------------------------------------------

    public static SoldierDecoder ReadNewSoldier(RobotController rc) throws GameActionException {
        return new SoldierDecoder(rc.readBroadcast(NEW_SOLDIER_CHANNEL));
    }

    public static SoldierCountDecoder ReadTypeAndGroup(RobotController rc, int soldierType, int group) throws GameActionException {
        int channel = soldierType * SoldierSpawner.MAX_GROUP_COUNT + group;
        int data = rc.readBroadcast(channel);

        if (data == 0) {
            return new SoldierCountDecoder(soldierType, group);
        }
        return new SoldierCountDecoder(data);
    }

    public static GroupCommandDecoder ReadFromGroup(RobotController rc, int group) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(rc.readBroadcast(GROUP_CHANNEL_BASE + group));
        decoder.ttl--;

        // No Coms yet on this channel
        // TODO: $DEBUG$
        rc.setIndicatorString(2, "Group Read(" + (GROUP_CHANNEL_BASE + group) + "): " + decoder.toString());

        // Shortcut it, clear the channel
        if (decoder.ttl <= 0) {

            // Clears channel
            ClearCommandChannel(rc, group);
            return new GroupCommandDecoder(0);
        }

        _Broadcast(rc, GROUP_CHANNEL_BASE + group, decoder);
        return decoder;
    }

    //-----------------------------------------------------
    // UTILS
    //-----------------------------------------------------

    public static void ClearCountChannel(RobotController rc, int soldierType, int group) throws GameActionException {
        _Broadcast(rc, soldierType * SoldierSpawner.MAX_GROUP_COUNT + group, 0);
    }

    public static void ClearCommandChannel(RobotController rc, int group) throws GameActionException {
        _Broadcast(rc, GROUP_CHANNEL_BASE + group, 0);
    }

    public static void ResetTimeToLiveGroupCommand(RobotController rc, int group) throws GameActionException {
        int channel = GROUP_CHANNEL_BASE + group;
        GroupCommandDecoder decoder = new GroupCommandDecoder(rc.readBroadcast(channel));
        decoder.resetTTL();
        _Broadcast(rc, channel, decoder.getData());
    }

    public static boolean ReadRound(int round) {
        return (round - 1) % RobotInformation.INFORMATION_ROUND_MOD == 0;
    }

    public static boolean WriteRound(int round) {
        return round % RobotInformation.INFORMATION_ROUND_MOD == 0;
    }

    private static void _Broadcast(RobotController rc, int channel, CommunicationDecoder decoder) throws GameActionException {
        _Broadcast(rc, channel, decoder.getData());

        // TODO: $DEBUG$
        rc.setIndicatorString(1, "Broadcasted(" + channel + "): " + decoder.getData() + " : " + decoder.toString());
    }

    private static void _Broadcast(RobotController rc, int channel, int data) throws GameActionException {
        rc.broadcast(channel, data);
    }

    /*****************************************************
     * Channel Constants
     *///*************************************************
    private static int NEW_SOLDIER_CHANNEL = 0;

    // Channels 1 through (MAX_GROUP_COUNT * SOLDIER_COUNT) + 1 are reserved for soldier communication
    private static int SOLDIER_TYPE_CHANNEL_BASE = 1;

    // Group channels go for group channel count + 5;
    private static int GROUP_CHANNEL_BASE = 1 + SoldierSpawner.MAX_GROUP_COUNT * SoldierSpawner.SOLDIER_COUNT;
}

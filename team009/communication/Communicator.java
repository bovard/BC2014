package team009.communication;

import battlecode.common.*;
import team009.robot.soldier.SoldierSpawner;

public class Communicator {

    //-----------------------------------------------------
    // WRITING
    //-----------------------------------------------------

    public static void WriteNewSoldier(RobotController rc, int soldierType, int group, MapLocation location) throws GameActionException {
        SoldierDecoder decoder = new SoldierDecoder(soldierType, group, location);
        _Broadcast(rc, NEW_SOLDIER_CHANNEL, decoder);
    }

    public static void WriteToGroup(RobotController rc, int group, int channel, int command, MapLocation location) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(group, command, location);
        _Broadcast(rc, _GroupChannel(group, channel), decoder);
    }

    public static void WriteToGroup(RobotController rc, int group, int channel, int command, MapLocation location, int ttl) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(group, command, location, ttl);
        _Broadcast(rc, _GroupChannel(group, channel), decoder);
    }

    public static void WriteTypeAndGroup(RobotController rc, int soldierType, int group) throws GameActionException {
        int channel = soldierType * MAX_GROUP_COUNT + group;
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
        int channel = soldierType * MAX_GROUP_COUNT + group;
        int data = rc.readBroadcast(channel);

        if (data == 0) {
            return new SoldierCountDecoder(soldierType, group);
        }
        return new SoldierCountDecoder(data);
    }

    public static GroupCommandDecoder ReadFromGroup(RobotController rc, int group, int channel) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(rc.readBroadcast(GROUP_CHANNEL_BASE + group));
        decoder.ttl--;

        // No Coms yet on this channel
        // TODO: $DEBUG$
        rc.setIndicatorString(channel, "Group Read(" + (GROUP_CHANNEL_BASE + group) + "): " + decoder.toString());

        // Shortcut it, clear the channel
        if (decoder.ttl <= 0) {

            // Clears channel
            ClearCommandChannel(rc, group, channel);
            return new GroupCommandDecoder(0);
        }

        _Broadcast(rc, _GroupChannel(group, channel), decoder);
        return decoder;
    }

    //-----------------------------------------------------
    // UTILS
    //-----------------------------------------------------

    public static void ClearCountChannel(RobotController rc, int soldierType, int group) throws GameActionException {
        _Broadcast(rc, soldierType * MAX_GROUP_COUNT + group, 0);
    }

    public static void ClearCommandChannel(RobotController rc, int group, int channel) throws GameActionException {
        _Broadcast(rc, _GroupChannel(group, channel), 0);
    }

    public static boolean ReadRound(int round) {
        return (round - 1) % INFORMATION_ROUND_MOD == 0;
    }

    public static boolean WriteRound(int round) {
        return round % INFORMATION_ROUND_MOD == 0;
    }

    private static void _Broadcast(RobotController rc, int channel, CommunicationDecoder decoder) throws GameActionException {
        _Broadcast(rc, channel, decoder.getData());
    }

    private static void _Broadcast(RobotController rc, int channel, int data) throws GameActionException {
        rc.broadcast(channel, data);
    }

    private static int _GroupChannel(int group, int channel) {
        return GROUP_CHANNEL_BASE + group * GROUP_CHANNEL_COUNT + channel;
    }

    /*****************************************************
     * Channel Constants
     *///*************************************************
    protected static int NEW_SOLDIER_CHANNEL = 0;

    // Channels 1 through (MAX_GROUP_COUNT * SOLDIER_COUNT) + 1 are reserved for soldier communication
    protected static int SOLDIER_TYPE_CHANNEL_BASE = 1;

    public static final int MAX_GROUP_COUNT = 5;
    public static final int GROUP_SOLDIER_CHANEL = 0;
    public static final int GROUP_HQ_CHANNEL = 1;
    public static final int GROUP_CENTROID_CHANNEL = 2;
    protected static final int GROUP_CHANNEL_COUNT = 3;
    protected static int GROUP_CHANNEL_BASE = SOLDIER_TYPE_CHANNEL_BASE + MAX_GROUP_COUNT * SoldierSpawner.SOLDIER_COUNT;

    // Group channels go for group channel count + 5;
    protected static final int INFORMATION_ROUND_MOD = 4;
}

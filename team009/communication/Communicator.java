package team009.communication;

import battlecode.common.*;
import team009.communication.decoders.*;
import team009.robot.TeamRobot;

public class Communicator {

    //-----------------------------------------------------
    // WRITING
    //-----------------------------------------------------

    public static void WriteNewSoldier(RobotController rc, int soldierType, int group, int comChannel, MapLocation location) throws GameActionException {
        SoldierDecoder decoder = new SoldierDecoder(soldierType, group, comChannel, location);
        _Broadcast(rc, NEW_SOLDIER_CHANNEL, decoder);
    }

    public static void WriteToGroup(RobotController rc, int group, int channel, int command, MapLocation location) throws GameActionException {
        WriteToGroup(rc, group, channel, command, location, -1);
    }

    public static void WriteToGroup(RobotController rc, int group, int channel, int command, MapLocation location, int ttl) throws GameActionException {
        GroupCommandDecoder decoder = new GroupCommandDecoder(group, command, location, ttl);

        if (channel == 1) {
            System.out.println("HQ Is Writing" + _GroupChannel(group, channel) + ": " + decoder.toString());
        } else {
        }
        _Broadcast(rc, _GroupChannel(group, channel), decoder);
    }

    public static void WriteTypeAndGroup(RobotController rc, int soldierType, int group, MapLocation location) throws GameActionException {
        int channel = soldierType * MAX_GROUP_COUNT + group;
        SoldierCountDecoder decoder = ReadTypeAndGroup(rc, soldierType, group);

        decoder.addSoldier(location);

        // Incs the channel
        _Broadcast(rc, channel, decoder);
    }

    // The two way communication
    public static TwoWayDecoder WriteTwoWayCommunicate(RobotController rc, int channel, int command, MapLocation from, MapLocation to) throws GameActionException {
        TwoWayDecoder dec = new TwoWayDecoder(from, to, command);
        _Broadcast(rc, channel, dec.getData());
        return dec;
    }

    public static void WritePassComChannel(RobotController rc, int comChannel, boolean sound) throws GameActionException {
        _Broadcast(rc, sound ? NOISE_TOWER_COM_PASS_CHANNEL : PASTR_COM_PASS_CHANNEL, new ComPassDecoder(comChannel));
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
            return new SoldierCountDecoder(soldierType, group, new MapLocation(0, 0));
        }

        return new SoldierCountDecoder(data);
    }

    public static GroupCommandDecoder ReadFromGroup(RobotController rc, int group, int channel) throws GameActionException {
        int groupChannel = _GroupChannel(group, channel);
        // TODO: $DEBUG$
        GroupCommandDecoder decoder = new GroupCommandDecoder(rc.readBroadcast(groupChannel));

        if (channel == 1) {
            System.out.println("Soldier is Reading" + _GroupChannel(group, channel) + ": " + decoder.toString());
        }

        // Shortcut it, clear the channel
        if (decoder.command == 0) {
            return new GroupCommandDecoder(0);
        }

        _Broadcast(rc, groupChannel, decoder.getData());
        return decoder;
    }

    // The two way communication
    public static TwoWayDecoder ReadTwoWayCommunicate(RobotController rc, int channel) throws GameActionException {
        return new TwoWayDecoder(rc.readBroadcast(channel));
    }

    public static int ReadPassComChannel(RobotController rc, boolean sound) throws GameActionException {
        return rc.readBroadcast(sound ? NOISE_TOWER_COM_PASS_CHANNEL : PASTR_COM_PASS_CHANNEL);
    }

    //-----------------------------------------------------
    // UTILS
    //-----------------------------------------------------

    public static void ClearCountChannel(RobotController rc, int soldierType, int group) throws GameActionException {
        _Broadcast(rc, soldierType * MAX_GROUP_COUNT + group, 0);
    }

    public static void ClearGroupChannel(RobotController rc, int group, int channel) throws GameActionException {
        _Broadcast(rc, _GroupChannel(group, channel), 0);
    }

    public static void ClearTwoWayChannel(RobotController rc, int channel) throws GameActionException {
        _Broadcast(rc, TWO_WAY_HQ_COM_BASE + channel, 0);
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
    protected static final int GROUP_CHANNEL_BASE = SOLDIER_TYPE_CHANNEL_BASE + MAX_GROUP_COUNT * TeamRobot.SOLDIER_COUNT;

    // The two way communications HQ < - > Soldier
    public static final int TWO_WAY_HQ_COM_BASE = GROUP_CHANNEL_BASE + MAX_GROUP_COUNT * GROUP_CHANNEL_COUNT;
    public static final int TWO_WAY_COM_LENGTH = 35;
    public static final int NOISE_TOWER_COM_PASS_CHANNEL = 4000;
    public static final int PASTR_COM_PASS_CHANNEL = 4010;

    // Group channels go for group channel count + 5;
    public static final int INFORMATION_ROUND_MOD = 4;
}

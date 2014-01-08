package team009.communication;

import battlecode.common.MapLocation;

public class GroupCommandDecoder extends CommunicationDecoder {
    public MapLocation location;
    public int command;
    public int group;
    public int ttl = 0;
    public boolean data = false;

    public GroupCommandDecoder(int group, int command) {
        this.command = command;
        this.group = group;
        location = null;
        ttl = TTL_MAX;
    }

    public GroupCommandDecoder(int group, int command, MapLocation location) {
        this.command = command;
        this.group = group;
        this.location = location;
        ttl = TTL_MAX;
    }

    public GroupCommandDecoder(int data) {
        if (data == 0) {
            this.data = false;
        }

        this.data = true;
        command = data / COMMAND_MULT;
        group = (data % COMMAND_MULT) / GROUP_MULT;
        location = MapDecoder.getLocationFromData(data);
    }

    public void resetTTL() {
        ttl = TTL_MAX;
    }

    @Override
    public int getData() {
        return (location != null ? MapDecoder.getDataFromLocation(location) : 0) +
                GROUP_MULT * group + COMMAND_MULT * command + TIME_TO_LIVE * ttl;
    }

    @Override
    public String toString() {
        return "Command: " + command + " to group " + group + " with location " + location;
    }

    public static final int GROUP_MULT = 1000000;
    public static final int COMMAND_MULT = 10000000;
    public static final int TIME_TO_LIVE = 100000000;
    public static final int TTL_MAX = 15;
}

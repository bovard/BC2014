package team009.communication;

import battlecode.common.MapLocation;

public class GroupCommandDecoder extends CommunicationDecoder {
    public MapLocation location;
    public int command;
    public int group;
    public int ttl = 0;

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
        ttl = data / TIME_TO_LIVE;
        command = (data % TIME_TO_LIVE) / COMMAND_MULT;
        group = (data % COMMAND_MULT) / GROUP_MULT;
        location = MapDecoder.getLocationFromData(data % GROUP_MULT);
    }

    protected void resetTTL() {
        ttl = TTL_MAX;
    }

    /**
     * If there is a command or not.
     */
    public boolean hasData() {
        return command != 0;
    }

    /**
     * if the ttl should be reset.
     */
    public boolean shouldResetTTL() {
        return ttl < 5;
    }

    @Override
    public int getData() {
        return (location != null ? MapDecoder.getDataFromLocation(location) : 0) +
                GROUP_MULT * group + COMMAND_MULT * command + TIME_TO_LIVE * ttl;
    }

    @Override
    public String toString() {
        return "Command: " + command + " to group " + group + " with location " + location + " With " + ttl + " to live.";
    }

    public static final int GROUP_MULT = MapDecoder.COMMAND_MULTIPLIER;
    public static final int COMMAND_MULT = GROUP_MULT * 10;
    public static final int TIME_TO_LIVE = COMMAND_MULT * 10;
    public static final int TTL_MAX = 25;
}

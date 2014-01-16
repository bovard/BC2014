package team009.communication;

import battlecode.common.MapLocation;
import team009.robot.TeamRobot;

public class GroupCommandDecoder extends CommunicationDecoder {
    public MapLocation location;
    public int command;
    public int group;
    public int ttl = 0;

    public GroupCommandDecoder(int group, int command, MapLocation location) {
        this.command = command;
        this.group = group;
        this.location = location;
        ttl = TTL_MAX;
    }

    public GroupCommandDecoder(int group, int command, MapLocation location, int ttl) {
        this.command = command;
        this.group = group;
        this.location = location;
        this.ttl = ttl == -1 ? TTL_MAX : ttl;
    }

    public GroupCommandDecoder(int data) {
        ttl = data / TIME_TO_LIVE;
        command = (data % TIME_TO_LIVE) / COMMAND_MULT;
        group = (data % COMMAND_MULT) / GROUP_MULT;
        location = MapDecoder.getLocationFromData(data % GROUP_MULT);
        decoderData = data;
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

    /**
     * Will see if the current decoder can be overriden.
     * @param current
     * @param command
     * @return
     */
    public static boolean shouldCommunicate(GroupCommandDecoder current, MapLocation loc, int command) {
        return shouldCommunicate(current, loc, command, false);
    }

    /**
     * Will see if the current decoder can be overriden.
     * @return
     */
    public static boolean shouldCommunicate(GroupCommandDecoder current, MapLocation loc, int command, boolean equals) {

        // If there is no data, overwrite
        if (current == null || !current.hasData()) {
            return true;
        }

        int currCom = current.command;
        if (currCom == command) {
            if (currCom == TeamRobot.RETURN_TO_BASE || loc.equals(current.location)) {
                return false;
            }

        }

        // If there is a defend command, overwrite
        if (equals && currCom <= command || currCom < command) {
            return true;
        }

        return false;
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

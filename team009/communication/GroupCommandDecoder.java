package team009.communication;

import battlecode.common.MapLocation;

public class GroupCommandDecoder extends CommunicationDecoder {
    MapLocation location;
    int command;
    int group;

    public GroupCommandDecoder(int group, int command) {
        this.command = command;
        this.group = group;
        this.location = null;
    }

    public GroupCommandDecoder(int group, int command, MapLocation location) {
        this.command = command;
        this.group = group;
        this.location = location;
    }

    public GroupCommandDecoder(int data) {
        command = data / COMMAND_MULT;
        group = (data % COMMAND_MULT) / GROUP_MULT;
        location = MapDecoder.getLocationFromData(data);
    }

    @Override
    public int getData() {
        return (location != null ? MapDecoder.getDataFromLocation(location) : 0) +
                GROUP_MULT * group + COMMAND_MULT * command;
    }

    @Override
    public String toString() {
        return "Command: " + command + " to group " + group + " with location " + location;
    }

    public static final int GROUP_MULT = 1000000;
    public static final int COMMAND_MULT = 10000000;
}

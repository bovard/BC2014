package team009.communication.decoders;

import battlecode.common.MapLocation;

public class TwoWayDecoder extends CommunicationDecoder {
    public MapLocation from, to;
    public int command;

    public TwoWayDecoder(MapLocation from, MapLocation to, int command) {
        this.from = from;
        this.to = to;
        this.command = command;
    }

    public TwoWayDecoder(int data) {
        this.from = TwoPositionMapDecoder.getLocationFromData(data, 1);
        this.to = TwoPositionMapDecoder.getLocationFromData(data, 2);
        this.command = data / TwoPositionMapDecoder.COMMAND_MULTIPLIER;
    }

    public int getData() {
        return TwoPositionMapDecoder.getDataFromLocation(from, 1) +
               TwoPositionMapDecoder.getDataFromLocation(to, 2) +
               TwoPositionMapDecoder.COMMAND_MULTIPLIER * command;
    }

    public String toString() {
        return "Command: " + command + " : " + from + " : " + to;
    }

}

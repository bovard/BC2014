package team009.communication.decoders;

import battlecode.common.MapLocation;
import team009.communication.decoders.CommunicationDecoder;

public class MapDecoder extends CommunicationDecoder {

	public MapLocation location;

	public MapDecoder(MapLocation location) {
		this.location = location;
	}

	public MapDecoder(int data) {
		int restOfData = data % COMMAND_MULTIPLIER;
		location = new MapLocation(restOfData / X_LOCATION_MULTIPLIER, restOfData % X_LOCATION_MULTIPLIER);
	}

	@Override
	public int getData() {
		return location.x * X_LOCATION_MULTIPLIER + location.y;
	}

    public static MapLocation getLocationFromData(int data) {
        int restOfData = data % COMMAND_MULTIPLIER;
        return new MapLocation(restOfData / X_LOCATION_MULTIPLIER, restOfData % X_LOCATION_MULTIPLIER);
    }

    public static int getDataFromLocation(MapLocation location) {
        return location.x * X_LOCATION_MULTIPLIER + location.y;
    }

	public static final int COMMAND_MULTIPLIER = 10000;
	public static final int X_LOCATION_MULTIPLIER = 100;
}

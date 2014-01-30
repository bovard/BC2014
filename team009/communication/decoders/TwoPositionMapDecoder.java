package team009.communication.decoders;

import battlecode.common.MapLocation;

public class TwoPositionMapDecoder {

    public static MapLocation getLocationFromData(int data, int position) {

        int locData = data % COMMAND_MULTIPLIER;
        int locationData = locData % MAP_POSITION_2;
        if (position == 2) {
            locationData = (data % COMMAND_MULTIPLIER) / MAP_POSITION_2;
        }
        return new MapLocation(locationData / X_LOCATION_MULTIPLIER, locationData % X_LOCATION_MULTIPLIER);
    }

    public static int getDataFromLocation(MapLocation location, int position) {
        if (position == 1) {
            return location.x * X_LOCATION_MULTIPLIER + location.y;
        }

        return location.x * X_LOCATION_MULTIPLIER * MAP_POSITION_2 + location.y * MAP_POSITION_2;
    }

    public static final int X_LOCATION_MULTIPLIER = 100;
    public static final int MAP_POSITION_2 = X_LOCATION_MULTIPLIER * 100;
    public static final int COMMAND_MULTIPLIER = MAP_POSITION_2 * 10000; //100,000,000 // room for up to 24 commands
}

package team009.communication.decoders;

import battlecode.common.MapLocation;

/**
 * Created by mpaulson on 1/21/14.
 */
public class TwoPositionMapDecoder {

    public static MapLocation getLocationFromData(int data, int position) {

        if (position == 1) {
            int restOfData = data % X_LOCATION_MULTIPLIER_2;
            return new MapLocation(restOfData / X_LOCATION_MULTIPLIER, restOfData % X_LOCATION_MULTIPLIER);
        }
        int restOfData = (data % COMMAND_MULTIPLIER);
        return new MapLocation(restOfData / X_LOCATION_MULTIPLIER_2, ((restOfData / X_LOCATION_MULTIPLIER_2) % X_LOCATION_MULTIPLIER) / MAP_POSITION_2);
    }

    public static int getDataFromLocation(MapLocation location, int position) {
        if (position == 1) {
            return location.x * X_LOCATION_MULTIPLIER + location.y;
        }

        return location.x * X_LOCATION_MULTIPLIER_2 + location.y * MAP_POSITION_2;
    }

    public static final int X_LOCATION_MULTIPLIER = 100;
    public static final int MAP_POSITION_2 = X_LOCATION_MULTIPLIER * 100;
    public static final int X_LOCATION_MULTIPLIER_2 = MAP_POSITION_2 * 100;
    public static final int COMMAND_MULTIPLIER = X_LOCATION_MULTIPLIER_2 * 100; //100,000,000 // room for up to 24 commands
}

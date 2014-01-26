package team009.communication.decoders;

import battlecode.common.MapLocation;
import team009.communication.decoders.CommunicationDecoder;

public class SoldierCountDecoder extends CommunicationDecoder {
    public int soldierType;
    public int group;
    public int count;
    public MapLocation centroid;

    public SoldierCountDecoder(int soldierType, int group, MapLocation centroid) {
        this.soldierType = soldierType;
        this.group = group;
        this.centroid = centroid;
        count = 0;
    }

    public SoldierCountDecoder(int data) {
        soldierType = data / SOLDIER_TYPE_MULTIPLIER;
        group = (data % SOLDIER_TYPE_MULTIPLIER) / GROUP_MULTIPLIER;
        count = (data % GROUP_MULTIPLIER) / COUNT_MULTIPLIER;
        centroid = MapDecoder.getLocationFromData(data);
    }

    @Override
    public int getData() {
        return soldierType * SOLDIER_TYPE_MULTIPLIER + group * GROUP_MULTIPLIER + count * COUNT_MULTIPLIER + MapDecoder.getDataFromLocation(centroid);
    }

    public void addSoldier(MapLocation loc) {
        if (count == 0) {
            centroid = loc;
            count++;
        } else {
            double x = centroid.x * count + loc.x;
            double y = centroid.y * count + loc.y;

            System.out.println("Centroid(" + count + "): " + centroid + " : (" + x + ", " + y + ") " + loc);
            count++;
            centroid = new MapLocation((int)Math.ceil(x / count), (int)Math.ceil(y / count));
        }
    }

    @Override
    public String toString() {
        return "Type: " + soldierType + " __ Count: " + count + " __ Group " + group + " : " + centroid;
    }

    public static final int COUNT_MULTIPLIER = MapDecoder.COMMAND_MULTIPLIER;
    public static final int GROUP_MULTIPLIER = COUNT_MULTIPLIER * 100;
    public static final int SOLDIER_TYPE_MULTIPLIER = GROUP_MULTIPLIER * 10;
}

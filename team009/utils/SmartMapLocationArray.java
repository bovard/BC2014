package team009.utils;

import battlecode.common.MapLocation;

public class SmartMapLocationArray {
    public MapLocation[] arr = new MapLocation[1000];
    public int length = 0;

    public SmartMapLocationArray() {

    }

    public void add(MapLocation n) {
        arr[length] = n;
        length++;
    }

    public MapLocation get(int index) {
        return arr[index];
    }

    public int size() {
        return length;
    }

}

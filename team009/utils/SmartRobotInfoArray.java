package team009.utils;

import battlecode.common.RobotInfo;

public class SmartRobotInfoArray {
    public RobotInfo[] arr = new RobotInfo[1000];
    public int length = 0;

    public SmartRobotInfoArray() {

    }

    public void add(RobotInfo n) {
        arr[length] = n;
        length++;
    }

    public RobotInfo get(int index) {
        return arr[index];
    }

    public int size() {
        return length;
    }

    public void clear() {
        length = 0;
    }

}
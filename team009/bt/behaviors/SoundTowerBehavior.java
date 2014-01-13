package team009.bt.behaviors;

import battlecode.common.*;
import team009.MapUtils;
import team009.robot.SoundTower;

public class SoundTowerBehavior extends Behavior {
    SoundTower tower;
    private int radius;
    private int angle;
    private int x;
    private int y;

    public SoundTowerBehavior(SoundTower robot) {
        super(robot);
        tower = robot;
        radius = MAX_DISTANCE;
        x = 0;
        y = 0;
        angle = 0;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
    }

    @Override
    public boolean run() throws GameActionException {
        //spin around in a cirle shooting the gun
        int x = (int) (radius * java.lang.Math.cos(java.lang.Math.toRadians(angle))) + (robot.currentLoc.x);
        int y = (int) (radius * java.lang.Math.sin(java.lang.Math.toRadians(angle))) + (robot.currentLoc.y);
        angle = angle+50;
        if(angle >= 360) {
            angle = 0;
            radius = radius-1;
            if(radius<=6) {
                radius = MAX_DISTANCE; //range of the noise tower
            }
        }
        MapLocation loc = new MapLocation(x,y);
        robot.rc.attackSquare(loc);
        return true;
    }

    private static final int MAX_DISTANCE = 18;
    private static final int MAX_DISTANCE_SQUARED = 400;
}


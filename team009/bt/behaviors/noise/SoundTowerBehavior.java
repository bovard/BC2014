package team009.bt.behaviors.noise;

import battlecode.common.*;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.robot.NoiseTower;

public class SoundTowerBehavior extends Behavior {
    NoiseTower tower;
    private int radius;
    private int angle;
    private int x;
    private int y;

    private Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    private int currentDir;

    private int towerStrat;
    private static final int TOWER_STRAT_PULL_CARDNIAL = 0;
    private static final int TOWER_STRAT_PULL_SPIRAL_SWEEP = 1;

    //MapLocation[] pastrLocs;
    MapLocation herdFocus;

    public SoundTowerBehavior(NoiseTower robot) {
        super(robot);
        tower = robot;
        radius = MAX_DISTANCE;
        x = 0;
        y = 0;
        angle = 0;
        currentDir = 0;
        towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
        //spin around in a cirle shooting the gun
        //TODO is pastrLocs within enviornment check????
        //pastrLocs = robot.rc.sensePastrLocations(robot.info.myTeam);
        //if(pastrLocs.length > 0) {
        //    herdFocus = pastrLocs[0];
        //}
        //else
        //{
            herdFocus = robot.currentLoc;
        //}


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
        MapLocation loc = null;

        boolean done = false;
        int count = 0;

        while(!done && count < 15) {
            switch(towerStrat)
            {
                case TOWER_STRAT_PULL_CARDNIAL:
                    loc = pullInCardinalDirections();
                    break;
                case TOWER_STRAT_PULL_SPIRAL_SWEEP:
                default:
                    loc = spiralSweep();
                    break;
            }

            done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc, robot.info.width, robot.info.height);
            count++;
        }


        robot.rc.attackSquare(loc);

        return true;
    }

    public MapLocation spiralSweep()
    {
        int x = (int) (radius * java.lang.Math.cos(java.lang.Math.toRadians(angle))) + (herdFocus.x);
        int y = (int) (radius * java.lang.Math.sin(java.lang.Math.toRadians(angle))) + (herdFocus.y);
        angle = angle+40;
        if(angle >= 360) {
            angle = 0;
            radius = radius-1;
            if(radius<=7) {
                radius = MAX_DISTANCE; //range of the noise tower
                //switch to other strat
                towerStrat = TOWER_STRAT_PULL_CARDNIAL;
            }
        }
        MapLocation loc = new MapLocation(x,y);
        return loc;
    }

    public MapLocation pullInCardinalDirections()
    {
        radius = radius - 1;
        if(radius <= 6) {
            radius = MAX_DISTANCE; //range of the noise tower
            currentDir++;
            if(currentDir == directions.length) {
                currentDir = 0;
                //switch to other strat
                towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
            }
        }
        Direction dir = directions[currentDir];
        MapLocation loc = robot.currentLoc.add(dir, radius);
        return loc;
    }

    private static final int MAX_DISTANCE = 18;
    private static final int MAX_DISTANCE_SQUARED = 400;
}


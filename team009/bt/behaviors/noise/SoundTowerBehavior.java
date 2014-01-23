//package team009.bt.behaviors.noise;
//
//import battlecode.common.*;
//import team009.bt.behaviors.Behavior;
//import team009.robot.NoiseTower;
//<<<<<<< HEAD
//import team009.utils.pathfinding.*;
//
//import java.util.ArrayList;
//import java.util.List;
//=======
//import team009.utils.CheesePostProcess;
//import team009.utils.MapPreProcessor;
//>>>>>>> 508e4c9aaccb1ce9dd1572c052548a0f291bb343
//
//public class SoundTowerBehavior extends Behavior {
//    NoiseTower tower;
//    private int radius;
//<<<<<<< HEAD
//    private int angle;
//    private int x;
//    private int y;
//
//    private Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
//    private int currentDir;
//
//    private int towerStrat;
//    private static final int TOWER_STRAT_PULL_CARDNIAL = 0;
//    private static final int TOWER_STRAT_PULL_SPIRAL_SWEEP = 1;
//    private static final int TOWER_STRAT_PULL_ROTATING = 2;
//
//
//    private int isAdjusted = 0;
//
//    private boolean[][] possibleLocations = new boolean[35][35];
//    private int xCheck = 0;
//    private int yCheck = 0;
//    private Point[] currentPath;
//    private MapLocation lastPoint;
//    private Point[][] cachedPaths = new Point[10][];
//    private int currentNode = 0;
//    private Direction lastDirection;
//    private double[][] cowSpots;
//    private GraphBuilder graphBuilder;
//
//    //Address this by the x adjust
//    private final int[] ValidY = {17,17,17,17,16,16,16,15,15,14,14,13,12,11,10,8,6,3};
//
//    //MapLocation[] pastrLocs;
//    MapLocation herdFocus;
//=======
//    private int[] radii;
//    private Direction currentDir;
//>>>>>>> 508e4c9aaccb1ce9dd1572c052548a0f291bb343
//
//    public SoundTowerBehavior(NoiseTower robot) {
//        super(robot);
//        tower = robot;
//<<<<<<< HEAD
//        radius = MAX_DISTANCE;
//        x = 0;
//        y = 0;
//        angle = 0;
//        currentDir = 0;
//        towerStrat = TOWER_STRAT_PULL_CARDNIAL;
//        //spin around in a cirle shooting the gun
//        //TODO is pastrLocs within enviornment check????
//        //pastrLocs = robot.rc.sensePastrLocations(robot.info.myTeam);
//        //if(pastrLocs.length > 0) {
//        //    herdFocus = pastrLocs[0];
//        //}
//        //else
//        //{
//            herdFocus = robot.rc.getLocation();
//        //}
//        cowSpots = this.rc.senseCowGrowth();
//=======
//        radii = CheesePostProcess.getBestDistances(robot.rc, robot.currentLoc);
//        currentDir = Direction.NORTH;
//        radius = radii[MapPreProcessor.DirectionToInt(currentDir)];
//>>>>>>> 508e4c9aaccb1ce9dd1572c052548a0f291bb343
//    }
//
//    @Override
//    public boolean pre() throws GameActionException {
//        return true;
//    }
//
//    @Override
//    public boolean post() throws GameActionException {
//        return false;
//    }
//
//    @Override
//    public void reset() throws GameActionException {
//    }
//
//    @Override
//    public boolean run() throws GameActionException {
//<<<<<<< HEAD
//        if(false && xCheck != 35) {
//            while(true) {
//                for(; xCheck < 35; xCheck++) {
//                    if(Clock.getBytecodeNum() > 8000) {
//                        return true;
//                    }
//
//                    for(; yCheck < 35; yCheck++) {
//                        int adjX = herdFocus.x - 17 + xCheck;
//                        int adjY = herdFocus.y - 17 + yCheck;
//
//                        if(adjX == robot.info.width || adjX == -1) {
//                            possibleLocations[xCheck][yCheck] = true;
//                        } else if(adjY == robot.info.height || adjY == -1) {
//                            possibleLocations[xCheck][yCheck] = true;
//                        } else {
//                            possibleLocations[xCheck][yCheck] = this.rc.senseTerrainTile(new MapLocation(adjX, adjY)) != TerrainTile.VOID;
//                        }
//                    }
//
//                    yCheck = 0;
//                }
//
//                createGraph();
//                return true;
//            }
//        } else {
//            MapLocation loc = null;
//
//            boolean done = false;
//            int count = 0;
//
//            while(!done && count < 15) {
//                switch(towerStrat)
//                {
//                    case TOWER_STRAT_PULL_ROTATING:
//                        loc = pullInRotatingCardinal();
//                        break;
//                    case TOWER_STRAT_PULL_CARDNIAL:
//                        loc = pullInCardinalDirections();
//                        break;
//                    case TOWER_STRAT_PULL_SPIRAL_SWEEP:
//                    default:
//                        loc = spiralSweep();
//                        break;
//                }
//
//
//                done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc.add(1,1), robot.info.width + 2, robot.info.height + 2);
//                count++;
//            }
//
//
//            robot.rc.attackSquare(loc);
//        }
//
//=======
//        robot.rc.attackSquare(pullInCardinalDirections());
//>>>>>>> 508e4c9aaccb1ce9dd1572c052548a0f291bb343
//        return true;
//    }
//
//    public MapLocation pullInCardinalDirections()
//    {
//<<<<<<< HEAD
//        radius = radius - 1;
//        if(radius <= 5) {
//            radius = MAX_DISTANCE; //range of the noise tower
//            currentDir++;
//            if(currentDir == directions.length) {
//                currentDir = 0;
//                //switch to other strat
//                towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
//            }
//        }
//
//        Direction dir = directions[currentDir];
//        MapLocation loc = robot.currentLoc.add(dir, radius);
//
//        return loc;
//    }
//
//    public MapLocation pullInRotatingCardinal()
//    {
//        int x = (int) (radius * java.lang.Math.cos(java.lang.Math.toRadians(angle))) + (herdFocus.x);
//        int y = (int) (radius * java.lang.Math.sin(java.lang.Math.toRadians(angle))) + (herdFocus.y);
//
//        radius = radius - 1;
//
//        if(radius <= 5) {
//            radius = MAX_DISTANCE; //range of the noise tower
//            currentDir++;
//
//            angle = angle+45;
//
//            if(angle >= 360) {
//                angle = 0 + isAdjusted == 0 ? 0 : 22;
//                isAdjusted = isAdjusted == 0 ? 1 : 0;
//                towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
//            }
//        }
//
//        MapLocation loc = new MapLocation(x,y);
//        return loc;
//    }
//
//    public void fillLocations() {
//        //Fills in the locations that are possible for cows to be herded on
//        // stores in the possibleLocations variable
//    }
//
//    public MapLocation pullInWaypoint()
//    {
//        if(currentPath == null || currentPath.length - currentNode < 3) {
//
//            Direction dir = directions[currentDir];
//            MapLocation loc = robot.currentLoc.add(dir, radius);
//
//            currentDir++;
//            if(currentDir == directions.length) {
//                currentDir = 0;
//            }
//
//            System.out.println("start");
//            //currentPath = findPath(herdFocus.x - loc.x + 17, herdFocus.y - loc.y + 17);
//            currentPath = findPath(10, 30);
//            System.out.println("done");
//            currentNode = 0;
//        }
//
//        if(currentNode < currentPath.length - 1) {
//            Point current = currentPath[currentNode];
//            Point next = currentPath[currentNode + 1];
//
//            MapLocation curLoc = new MapLocation(current.x, current.y);
//            MapLocation nextLoc = new MapLocation(next.x, next.y);
//
//            Direction nextDir = curLoc.directionTo(nextLoc);
//
//            if(lastDirection == null) {
//                lastDirection = nextDir;
//            }
//
//            if(nextDir != lastDirection) {
//                lastDirection = nextDir;
//                return new MapLocation(herdFocus.x + currentPath[currentNode].x - 17, herdFocus.y + currentPath[currentNode].y - 17).subtract(nextDir);
//            }
//        }
//
//
//        if(currentPath.length > 0) {
//            return new MapLocation(herdFocus.x + currentPath[currentNode].x - 17, herdFocus.y + currentPath[currentNode++].y - 17);
//        }
//
//        return new MapLocation(herdFocus.x, herdFocus.y);
//    }
//
//    public Point[] findPath(int x, int y) {
//        return graphBuilder.getPath(new Point(x, y), new Point(17, 17));
//    }
//
//    public void createGraph ()
//    {
//        graphBuilder = new GraphBuilder(35,35);
//
//
//        for(int i = 0; i < possibleLocations.length; i++) {
//            for(int j = 0; j < possibleLocations[0].length; j++) {
//                if(!possibleLocations[i][j]) {
//                    graphBuilder.addObstacle(new Point(i,j));
//                }
//            }
//        }
//
//        graphBuilder.buildMatrix();
//    }
//
//    public static int pairingFunction(int x, int y) {
//        return ((x + y) * (x + y + 1) >> 1) + y;
//    }
//
//    private static final int MAX_DISTANCE = 17;
//    private static final int MAX_DISTANCE_SQUARED = 300;
//=======
//        int i = 0;
//        while (radius <= 3 && i < 3) {
//            currentDir = currentDir.rotateRight();
//            radius = radii[MapPreProcessor.DirectionToInt(currentDir)];
//            i++;
//        }
//        MapLocation loc = robot.currentLoc.add(currentDir, radius);
//        radius--;
//
//        return loc;
//    }
//>>>>>>> 508e4c9aaccb1ce9dd1572c052548a0f291bb343
//}
//

package team009.bt.behaviors.noise;

import battlecode.common.*;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.robot.NoiseTower;
import team009.utils.pathfinding.*;

import java.util.ArrayList;
import java.util.List;

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
    private static final int TOWER_STRAT_PULL_ROTATING = 2;


    private int isAdjusted = 0;

    private boolean[][] possibleLocations = new boolean[35][35];
    private int xCheck = 0;
    private int yCheck = 0;
    private Point[] currentPath;
    private MapLocation lastPoint;
    private Point[][] cachedPaths = new Point[10][];
    private int currentNode = 0;
    private Direction lastDirection;
    private double[][] cowSpots;
    private GraphBuilder graphBuilder;

    //Address this by the x adjust
    private final int[] ValidY = {17,17,17,17,16,16,16,15,15,14,14,13,12,11,10,8,6,3};

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
        towerStrat = TOWER_STRAT_PULL_CARDNIAL;
        //spin around in a cirle shooting the gun
        //TODO is pastrLocs within enviornment check????
        //pastrLocs = robot.rc.sensePastrLocations(robot.info.myTeam);
        //if(pastrLocs.length > 0) {
        //    herdFocus = pastrLocs[0];
        //}
        //else
        //{
        herdFocus = robot.rc.getLocation();
        //}
        cowSpots = this.rc.senseCowGrowth();
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
        if(false && xCheck != 35) {
            while(true) {
                for(; xCheck < 35; xCheck++) {
                    if(Clock.getBytecodeNum() > 8000) {
                        return true;
                    }

                    for(; yCheck < 35; yCheck++) {
                        int adjX = herdFocus.x - 17 + xCheck;
                        int adjY = herdFocus.y - 17 + yCheck;

                        if(adjX == robot.info.width || adjX == -1) {
                            possibleLocations[xCheck][yCheck] = true;
                        } else if(adjY == robot.info.height || adjY == -1) {
                            possibleLocations[xCheck][yCheck] = true;
                        } else {
                            possibleLocations[xCheck][yCheck] = this.rc.senseTerrainTile(new MapLocation(adjX, adjY)) != TerrainTile.VOID;
                        }
                    }

                    yCheck = 0;
                }

                createGraph();
                return true;
            }
        } else {
            MapLocation loc = null;

            boolean done = false;
            int count = 0;

            while(!done && count < 15) {
                switch(towerStrat)
                {
                    case TOWER_STRAT_PULL_ROTATING:
                        loc = pullInRotatingCardinal();
                        break;
                    case TOWER_STRAT_PULL_CARDNIAL:
                        loc = pullInCardinalDirections();
                        break;
                    case TOWER_STRAT_PULL_SPIRAL_SWEEP:
                    default:
                        loc = spiralSweep();
                        break;
                }


                done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc.add(1,1), robot.info.width + 2, robot.info.height + 2);
                count++;
            }


            robot.rc.attackSquare(loc);
        }

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
        if(radius <= 5) {
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

    public MapLocation pullInRotatingCardinal()
    {
        int x = (int) (radius * java.lang.Math.cos(java.lang.Math.toRadians(angle))) + (herdFocus.x);
        int y = (int) (radius * java.lang.Math.sin(java.lang.Math.toRadians(angle))) + (herdFocus.y);

        radius = radius - 1;

        if(radius <= 5) {
            radius = MAX_DISTANCE; //range of the noise tower
            currentDir++;

            angle = angle+45;

            if(angle >= 360) {
                angle = 0 + isAdjusted == 0 ? 0 : 22;
                isAdjusted = isAdjusted == 0 ? 1 : 0;
                towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
            }
        }

        MapLocation loc = new MapLocation(x,y);
        return loc;
    }

    public void fillLocations() {
        //Fills in the locations that are possible for cows to be herded on
        // stores in the possibleLocations variable
    }

    public MapLocation pullInWaypoint()
    {
        if(currentPath == null || currentPath.length - currentNode < 3) {

            Direction dir = directions[currentDir];
            MapLocation loc = robot.currentLoc.add(dir, radius);

            currentDir++;
            if(currentDir == directions.length) {
                currentDir = 0;
            }

            System.out.println("start");
            //currentPath = findPath(herdFocus.x - loc.x + 17, herdFocus.y - loc.y + 17);
            currentPath = findPath(10, 30);
            System.out.println("done");
            currentNode = 0;
        }

        if(currentNode < currentPath.length - 1) {
            Point current = currentPath[currentNode];
            Point next = currentPath[currentNode + 1];

            MapLocation curLoc = new MapLocation(current.x, current.y);
            MapLocation nextLoc = new MapLocation(next.x, next.y);

            Direction nextDir = curLoc.directionTo(nextLoc);

            if(lastDirection == null) {
                lastDirection = nextDir;
            }

            if(nextDir != lastDirection) {
                lastDirection = nextDir;
                return new MapLocation(herdFocus.x + currentPath[currentNode].x - 17, herdFocus.y + currentPath[currentNode].y - 17).subtract(nextDir);
            }
        }


        if(currentPath.length > 0) {
            return new MapLocation(herdFocus.x + currentPath[currentNode].x - 17, herdFocus.y + currentPath[currentNode++].y - 17);
        }

        return new MapLocation(herdFocus.x, herdFocus.y);
    }

    public Point[] findPath(int x, int y) {
        return graphBuilder.getPath(new Point(x, y), new Point(17, 17));
    }

    public void createGraph ()
    {
        graphBuilder = new GraphBuilder(35,35);


        for(int i = 0; i < possibleLocations.length; i++) {
            for(int j = 0; j < possibleLocations[0].length; j++) {
                if(!possibleLocations[i][j]) {
                    graphBuilder.addObstacle(new Point(i,j));
                }
            }
        }

        graphBuilder.buildMatrix();
    }

    public static int pairingFunction(int x, int y) {
        return ((x + y) * (x + y + 1) >> 1) + y;
    }

    private static final int MAX_DISTANCE = 17;
    private static final int MAX_DISTANCE_SQUARED = 300;
}



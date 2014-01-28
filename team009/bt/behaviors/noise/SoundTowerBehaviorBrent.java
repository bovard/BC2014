package team009.bt.behaviors.noise;

import battlecode.common.*;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.robot.NoiseTower;
import team009.utils.pathfinding.*;

import java.util.ArrayList;
import java.util.List;

public class SoundTowerBehaviorBrent extends Behavior {
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
    private static final int TOWER_STRAT_PULL_WAYPOINT = 3;


    private int isAdjusted = 0;

    private boolean[][] possibleLocations = new boolean[35][35];
    private int xCheck = 0;
    private int yCheck = 0;
    private int obsX = 0;
    private int obsY = 0;
    private boolean isDone = false;
    private Point[] currentPath;
    private MapLocation lastPoint;
    private Point[][] cachedPaths = new Point[8][];
    private int currentNode = 0;
    private Direction lastDirection;
    private double[][] cowSpots;
    private GraphBuilder graphBuilder;
    private MapLocation lastPosition;

    //Address this by the x adjust
    private final int[] ValidY = {17,17,17,17,16,16,16,15,15,14,14,13,12,11,10,8,6,3};

    //MapLocation[] pastrLocs;
    MapLocation herdFocus;

    public SoundTowerBehaviorBrent(NoiseTower robot) {
        super(robot);
        tower = robot;
        radius = MAX_DISTANCE;
        x = 0;
        y = 0;
        angle = 0;
        currentDir = 0;
        towerStrat = TOWER_STRAT_PULL_WAYPOINT;
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
        MapLocation loc = null;
        if(towerStrat == TOWER_STRAT_PULL_WAYPOINT && !isDone) {
            outerloop:
            while(true) {
                for(; xCheck < 35; xCheck++) {
                    if(Clock.getBytecodeNum() > 8000) {
                        boolean done = false;
                        while(!done) {
                            loc = pullInCardinalDirections();
                            done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc.add(2,2), robot.info.width + 4, robot.info.height + 4);
                        }

                        break outerloop;
                    }

                    for(; yCheck < 35; yCheck++) {
                        int adjX = herdFocus.x - 17 + xCheck;
                        int adjY = herdFocus.y - 17 + yCheck;
                        possibleLocations[xCheck][yCheck] = this.rc.senseTerrainTile(new MapLocation(adjX, adjY)) != TerrainTile.VOID;
                    }

                    yCheck = 0;
                }

                createGraph();

                if(!isDone) {
                    boolean done = false;
                    while(!done) {
                        loc = pullInCardinalDirections();
                        done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc.add(2,2), robot.info.width + 4, robot.info.height + 4);
                    }

                    break outerloop;
                }

                return true;
            }
        } else {
            boolean done = false;
            int count = 0;

            while(!done && count < 15) {
                switch(towerStrat)
                {
                    case TOWER_STRAT_PULL_WAYPOINT:
                        loc = pullInWaypoint();
                        break;
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

                done = rc.canAttackSquare(loc) && MapUtils.isOnMap(loc.add(2,2), robot.info.width + 4, robot.info.height + 4);
                count++;
            }
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
        if(radius <= 5) {
            radius = MAX_DISTANCE; //range of the noise tower
            currentDir++;
            if(currentDir == directions.length) {
                currentDir = 0;
                //switch to other strat
                //towerStrat = TOWER_STRAT_PULL_SPIRAL_SWEEP;
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
        if(lastPosition != null && manhattan(new Point(lastPosition.x, lastPosition.y), new Point(17, 17)) <= 5) {
            currentPath = null;
        }

        if(currentPath == null || currentPath.length - currentNode < 1) {
            Direction dir = directions[currentDir];

            MapLocation loc = robot.currentLoc.add(dir, radius);
            if(!MapUtils.isOnMap(loc, robot.info.width, robot.info.height) || rc.senseTerrainTile(loc) == TerrainTile.VOID) {
                return pullInCardinalDirections();
            }


            radius = MAX_DISTANCE;
            lastPosition = new MapLocation(loc.x - herdFocus.x + 17, loc.y - herdFocus.y + 17);

            if(cachedPaths[currentDir] == null || cachedPaths[currentDir].length == 0) {
                currentPath = findPath(lastPosition.x, lastPosition.y);
                cachedPaths[currentDir] = currentPath;
            } else {
                currentPath = cachedPaths[currentDir];
            }

            currentDir++;
            if(currentDir == directions.length) {
                currentDir = 0;
            }
            currentNode = 0;
        }

        if(currentNode < currentPath.length) {
            MapLocation currentTarget =  new MapLocation(currentPath[currentNode].x, currentPath[currentNode].y);
            lastPosition = lastPosition.add(lastPosition.directionTo(currentTarget));

            if(manhattan(new Point(currentTarget.x, currentTarget.y), new Point(lastPosition.x, lastPosition.y)) <= 2) {
                currentNode++;
                lastPosition = lastPosition.add(lastPosition.directionTo(currentTarget), 3);

                if(currentNode < currentPath.length) {
                    currentTarget = new MapLocation(currentPath[currentNode].x, currentPath[currentNode].y);
                    lastPosition = lastPosition.add(lastPosition.directionTo(currentTarget), -4);
                }
            }
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
//                lastPosition = new MapLocation(herdFocus.x + currentPath[currentNode].x - 17, herdFocus.y + currentPath[currentNode].y - 17).subtract(nextDir);
//                return lastPosition;
//            }
        }


        if(currentPath.length > 0) {
            return new MapLocation(herdFocus.x + lastPosition.x - 17, herdFocus.y + lastPosition.y - 17);
        }

        return new MapLocation(herdFocus.x, herdFocus.y);
    }

    public Point[] findPath(int x, int y) {
        return graphBuilder.getPath(new Point(x, y), new Point(17, 17));
    }

    public void createGraph ()
    {
        if(graphBuilder == null)
            graphBuilder = new GraphBuilder(35,35);

        for(; obsX < possibleLocations.length; obsX++) {
            for(; obsY < possibleLocations[obsX].length; obsY++) {
                if(!possibleLocations[obsX][obsY]) {

                    graphBuilder.addObstacle(new Point(obsX, obsY));
                }
            }
            obsY = 0;
        }

        isDone = graphBuilder.buildMatrix();
    }

    public static int pairingFunction(int x, int y) {
        return ((x + y) * (x + y + 1) >> 1) + y;
    }

    private int manhattan(Point p1, Point p2) {
        if(p1.x > p2.x) {
            if(p1.y > p2.y) {
                return p1.x - p2.x + p1.y - p2.y;
            } else {
                return p1.x - p2.x + p2.y - p1.y;
            }
        } else {
            if(p1.y > p2.y) {
                return p2.x - p1.x + p1.y - p2.y;
            } else {
                return p2.x - p1.x + p2.y - p1.y;
            }
        }
    }

    private static final int MAX_DISTANCE = 15;
    private static final int MAX_DISTANCE_SQUARED = 300;
}

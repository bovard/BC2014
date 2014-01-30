package team009.utils;

import battlecode.common.MapLocation;
import team009.BehaviorConstants;
import team009.utils.pathfinding.IntHeap;

import java.util.ArrayList;
import java.util.Arrays;

public class AStar {
    private static final int X = 0;
    private static final int Y = 1;
    private static final double SQRT_2 = Math.sqrt(2);


    // things we need to know
    private int[][] map;
    private int minMapValue;
    private int[][] pathCache;
    private int numCoarseSquaresX;
    private int numCoarseSquaresY;
    private int numMapLocationsPerCoarseSquareX;
    private int numMapLocationsPerCoarseSquareY;
    private int numNodes;


    // things we need to set-up tear-down for every run
    public boolean busy = false;
    private int[] f_scores; // the estimated cost of getting to the goal
    private int[] g_scores; // the cost of getting here
    private int[] cameFrom;
    private IntHeap open; // the list of nodes to evaluate
    private SmartIntArray closed; // the list of nodes already evaluated
    private int startSquare;
    private int endSquare;


    /**
     * AStar! Needs a map of int values for the square and the minMapValue
     * @param map the value-filled coarse map
     * @param minMapValue the min value on the coarse map
     * @param numMapLocationsPerCoarseSquareX the number of MapLocations x per coarse square
     * @param numMapLocationsPerCoarseSquareY the number of MapLocations y per coarse square
     */
    public AStar(int[][] map, int minMapValue, int numMapLocationsPerCoarseSquareX, int numMapLocationsPerCoarseSquareY) {
        this.map = map;
        this.minMapValue = minMapValue;
        numCoarseSquaresX = map.length;
        numCoarseSquaresY = map[0].length;
        this.numMapLocationsPerCoarseSquareX = numMapLocationsPerCoarseSquareX;
        this.numMapLocationsPerCoarseSquareY = numMapLocationsPerCoarseSquareY;
        numNodes = numCoarseSquaresX * numCoarseSquaresY;
        pathCache = new int[numNodes][numNodes];
    }

    private int _mapLocationToSquareID(MapLocation loc) {
        //System.out.println("MapLocation to SquareID");
        //System.out.println("loc x/y = " + loc.x + "/" + loc.y);
        return numCoarseSquaresY * (loc.x / numMapLocationsPerCoarseSquareX) + loc.y / numMapLocationsPerCoarseSquareY;
    }


    private void _printSquareMapLocationCenter(int squareID) {
        MapLocation m = _getSquareCenterFromSquareID(squareID);
        System.out.println("MapLocation center for squareID " + squareID + " is " + m.toString());
    }

    private MapLocation _getSquareCenterFromSquareID(int squareID) {
        int x = squareID / numCoarseSquaresY;
        int y = squareID % numCoarseSquaresY;
        x = ((2 * x + 1) * numMapLocationsPerCoarseSquareX)/2;
        y = ((2 * y + 1) * numMapLocationsPerCoarseSquareY)/2;
        return new MapLocation(x, y);
    }

    /**
     * Checks the cache for a path from current to destination
     * @param currentLocation
     * @param destination
     * @return null if it's not in the cache, the next waypoint if it is
     */
    public MapLocation getCachedWayPoint(MapLocation currentLocation, MapLocation destination) {
        int startSquare = _mapLocationToSquareID(currentLocation);
        int endSquare = _mapLocationToSquareID(destination);
        if (pathCache[startSquare][endSquare] != 0) {
            if (pathCache[startSquare][endSquare] == -1) {
                return _getSquareCenterFromSquareID(0);
            }
            return _getSquareCenterFromSquareID(pathCache[startSquare][endSquare]);
        }

        return null;
    }

    /**
     * Given your a currentLocation and destination returns the next MapLocation waypoint you should go to
     * @param currentLocation the location of the bot
     * @param destination the location the bot would like to go to
     * @return
     */
    public MapLocation getNextWayPoint(MapLocation currentLocation, MapLocation destination) {
        if (_mapLocationToSquareID(currentLocation) == _mapLocationToSquareID(destination)) {
            return destination;
        }
        int result = _getNextSquare(_mapLocationToSquareID(currentLocation), _mapLocationToSquareID(destination));
        if (result == -2) {
            // something went wrong with the run, restart it!
            busy = false;
            return null;
        } else if (result == -1) {
            // we aren't done processing yet
            return null;
        } else {
            // we are done!
            busy = false;
            return _getSquareCenterFromSquareID(result);
        }
    }


    /**
     * Returns the next square the robot should move to if attempting to to got endSquare
     * from startSquare, uses a cahce if possible
     * @param startSquare the squareID you're starting at
     * @param endSquare the squareID you're trying to get to
     * @return the next square you should move to or null if not done processing
     */
    private int _getNextSquare(int startSquare, int endSquare) {
        //System.out.println("_getNextSquare " + startSquare + " to " + endSquare);
        //System.out.println(pathCache.length + " " + pathCache[0].length + " " + pathCache[0][1]);
        //System.out.println("Path cache is " + pathCache[startSquare][endSquare]);
        if (pathCache[startSquare][endSquare] != 0) {
            // checked to see if it's cached first
            if (pathCache[startSquare][endSquare] == -1) {
                return 0;
            }
            return pathCache[startSquare][endSquare];
        } else {
            // if it's not start a new run if we aren't currently doing one
            if (!busy) {
                _startNewRun(startSquare, endSquare);
                busy = true;
            }
            return _loop();
        }

    }

    private void _startNewRun(int startSquare, int endSquare) {
        busy = true;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        f_scores = new int[numNodes]; // the estimated cost of getting to the goal
        g_scores = new int[numNodes]; // the cost of getting here
        cameFrom = new int[numNodes];

        open = new IntHeap(10000); // the list of nodes to evaluate
        closed = new SmartIntArray(); // the list of nodes already evaluated
        g_scores[startSquare] = 0;
        f_scores[startSquare] = _heuristic(startSquare, endSquare);
        open.add(startSquare, f_scores[startSquare]);
    }


    private int _loop() {
        if (!open.isEmpty()) {
            System.out.println("Starting a loop");
            System.out.println("=====================================================");
            Timer.StartTimer();
            int current = open.pop();

            if (current == endSquare) {
                return _cacheAndReturnNextNode(cameFrom, startSquare, endSquare);
            }

            closed.add(current);

            SmartIntArray neighbors = _neighbors(current);

            for (int i = neighbors.length - 1 ; i >= 0; i--) {
                int neighbor = neighbors.arr[i];

                if (closed.contains(neighbor)) {
                    continue;
                }

                int tentative_score = g_scores[neighbor] + _distBetween(neighbor, current);

                if (!open.contains(neighbor) || g_scores[neighbor] > tentative_score) {
                    cameFrom[neighbor] = current;
                    g_scores[neighbor] = tentative_score;
                    f_scores[neighbor] = g_scores[neighbor] + _heuristic(neighbor, endSquare);
                    if (!open.contains(neighbor)) {
                        open.add(neighbor, f_scores[neighbor]);
                    }
                }
            }
            System.out.println("Ending a loop =====================================================");
            Timer.EndTimer();
            System.out.println("Size of open " + open.size());
            System.out.println("Size of closed " + closed.size());

        } else {
            return -2;
        }
        return -1;
    }

    /**
     * Given a node number, returns the node number of it's non-impassible on-map neighbors
     * @param loc the node number
     * @return the non-impassible neightbors
     */
    private SmartIntArray _neighbors(int loc) {
        //System.out.println("_neighbors");
        SmartIntArray neighbors = new SmartIntArray();
        int x = loc / numCoarseSquaresY;
        int y = loc % numCoarseSquaresY;

        //System.out.println("Looking at neighbors for " + loc);
        // if we are safe in the middle of the map add the surrounding 8
        if (x > 0 && y > 0 && x < numCoarseSquaresX - 2 && y < numCoarseSquaresY - 2) {
            // 1, 1
            if (map[x + 1][y + 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x + 1) + y + 1);
            }
            // 1, 0
            if (map[x + 1][y] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x + 1) + y);
            }
            // 1, -1
            if (map[x + 1][y - 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x + 1) + y - 1);
            }
            // 0, 1
            if (map[x][y + 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * x + y + 1);
            }
            // 0, -1
            if (map[x][y - 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * x  + y - 1);
            }
            // -1, 1
            if (map[x - 1][y + 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x - 1) + y + 1);
            }
            // -1, 0
            if (map[x - 1][y] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x - 1) + y);
            }
            // -1, -1
            if (map[x - 1][y - 1] < BehaviorConstants.IMPASSIBLE) {
                neighbors.add(numCoarseSquaresY * (x - 1) + y - 1);
            }

        }
        // else we have to do a safe add
        // TODO: get this out of for loops to optimize! there are 2 top level if and 2 sub levels for each top level
        else {
            for (int i = -1; i <= 1; i ++) {
                for (int j = -1; j <= 1; j ++) {
                    if ((i!= 0 || j!=0) && x + i >= 0 && x + i < numCoarseSquaresX && y + j >= 0 && y + j < numCoarseSquaresY) {
                        if (map[x + i][y + j] < BehaviorConstants.IMPASSIBLE) {
                            //System.out.println("Adding " + (numCoarseSquaresY * (x + i) + (y + j)));
                            neighbors.add( numCoarseSquaresY * (x + i) + (y + j));
                        }
                    }
                }
            }

        }
        return neighbors;
    }

    /**
     * this returns the approximate time to travel to through the neighbor node
     * if it's orthagonal this will just be the nodes value
     * if it's diagonal it will be sqrt(2) * the nodes value
     * @param neighbor
     * @param current
     * @return
     */
    private int _distBetween(int neighbor, int current) {
        if (Math.abs(current - neighbor) <= numCoarseSquaresY) {
            // the squares are orthagonal
            return map[neighbor/ numCoarseSquaresY][neighbor % numCoarseSquaresY];
        } else {
            // the squares are diagonal
            return (int)(SQRT_2 *  map[neighbor/ numCoarseSquaresY][neighbor % numCoarseSquaresY]);
        }

    }


    /**
     * Given that we've computed the path and stored it in cameFrom, caches the solution
     * and returns the next node to go to from cameFrom to get to goal
     * @param cameFrom the computed path
     * @param start the start node
     * @param goal the goal node
     * @return
     */
    private int _cacheAndReturnNextNode(int[] cameFrom, int start, int goal) {
        //System.out.println("Cache And Return");
        System.out.println("Goal " + goal + " start " + start);
        System.out.println("From goal working backwards, you should go:");
        _printSquareMapLocationCenter(goal);
        int current = cameFrom[goal];
        _printSquareMapLocationCenter(current);
        if (goal == 0) {
            pathCache[current][goal] = -1;
        } else {
            pathCache[current][goal] = goal;
        }

        if (current == start) {
            _printSquareMapLocationCenter(start);
            return goal;
        }

        int previous = cameFrom[current];
        do {
            if (current == 0) {
                pathCache[previous][goal] = -1;
            } else {
                pathCache[previous][goal] = current;
            }
            int temp = previous;
            previous = cameFrom[previous];
            current = temp;
            _printSquareMapLocationCenter(current);
        } while (previous != start && current != start);

        if (current == 0) {
            pathCache[start][goal] = -1;
        } else {
            pathCache[start][goal] = current;
        }
        _printSquareMapLocationCenter(start);

        return current;
    }


    /**
     * looks through all the node ints in open for the one with the lowest f
     * @param open
     * @param f_scores
     * @return
     */
    @Deprecated
    private static int _findLowestF(ArrayList<Integer> open, int[] f_scores) {
        // Note: This isn't called any more!
        // TODO: this is called a lot, optimize the sh*t out of it
        // TODO: move this up to the main method to save on bytecodes
        //System.out.println("_findLowestF");
        int min = Integer.MAX_VALUE;
        int minLoc = -1;
        for (int loc : open) {
            if (f_scores[loc] < min) {
                minLoc = loc;
                min = f_scores[loc];
            }

        }
        // if minLoc = -1 here something is very wrong!
        if (minLoc == -1) {
            System.out.println("WE ARE IN TROUBLE");
        }
        //System.out.println("Found " + minLoc + " at distance " + min);
        return minLoc;
    }


    private int _heuristic(int startNode, int goalNode) {
        // TODO: this is called a lot, optimize the sh*t out of it
        int[] start = new int[]{startNode/ numCoarseSquaresY, startNode % numCoarseSquaresY};
        int [] goal = new int[]{goalNode/ numCoarseSquaresY, goalNode % numCoarseSquaresY};
        return (int)(Math.sqrt(Math.pow(start[X] - goal[X],2) + Math.pow(start[Y] - goal[Y],2)) * minMapValue);
    }




}

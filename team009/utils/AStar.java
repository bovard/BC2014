package team009.utils;

import battlecode.common.MapLocation;
import team009.BehaviorConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class AStar {
    private static final int X = 0;
    private static final int Y = 1;
    private static final double SQRT_2 = Math.sqrt(2);


    private int[][] map;
    private int minMapValue;
    private int[][] pathCache;
    private int numCoarseSquaresX;
    private int numCoarseSquaresY;
    private int numMapLocationsPerCoarseSquareX;
    private int numMapLocationsPerCoarseSquareY;
    private int numNodes;

    /**
     * AStar! Needs a map of int values for the square and the minMapValue
     * @param map the value-filled coarse map
     * @param minMapValue the min value on the coarse map
     * @param numMapLocationsPerCoarseSquareX the number of MapLocations x per coarse square
     * @param numMapLocationsPerCoarseSquareY the nubmer of MapLocations y per coarse square
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
        for (int i = numNodes - 1; i >= 0; i--) {
            Arrays.fill(pathCache[i], -1);
        }
        System.out.println("Course map is " + numCoarseSquaresX + " by " + numCoarseSquaresY);
    }

    public int mapLocationToSquareID(MapLocation loc, int coarseHeight, int coarseWidth) {
        System.out.println("MapLocation to SquareID");
        System.out.println("loc x/y = " + loc.x + "/" + loc.y);
        return numCoarseSquaresY * (loc.x / coarseWidth) + loc.y / coarseHeight;
    }


    private void _printSquareMapLocationCenter(int squareID) {
        MapLocation m = getSquareCenterFromSquareID(squareID);
        System.out.println("MapLocation center for squareID " + squareID + " is " + m.toString());
    }

    public MapLocation getSquareCenterFromSquareID(int squareID) {
        int x = squareID / numCoarseSquaresY;
        int y = squareID % numCoarseSquaresY;
        x = (x * numMapLocationsPerCoarseSquareX + x * (numMapLocationsPerCoarseSquareX + 1))/2;
        y = (y * numMapLocationsPerCoarseSquareX + y * (numMapLocationsPerCoarseSquareX + 1))/2;
        return new MapLocation(x, y);
    }


    /**
     * Returns the next square the robot should move to if attempting to to got endSquare
     * from startSquare, uses a cahce if possible
     * @param startSquare the squareID you're starting at
     * @param endSquare the squareID you're trying to get to
     * @return the next square you should move to
     */
    public int getNextSquare(int startSquare, int endSquare) {
        System.out.println("getNextSquare " + startSquare + " to " + endSquare);
        System.out.println(pathCache.length + " " + pathCache[0].length + " " + pathCache[0][1]);
        System.out.println("Path cache is " + pathCache[startSquare][endSquare]);
        if (pathCache[startSquare][endSquare] != -1) {
            return pathCache[startSquare][endSquare];
        } else {
            return _findPath(startSquare, endSquare);
        }

    }


    private int _findPath(int startSquare, int endSquare) {
        int[] f_scores = new int[numNodes]; // the estimated cost of getting to the goal
        int[] g_scores = new int[numNodes]; // the cost of getting here
        int[] cameFrom = new int[numNodes];

        // TODO: open could be a min heap so we can avoid searching it every time
        // if this was a min heap we could just always remove the 0th element
        // easily could create a class to do this?
        ArrayList<Integer> open = new ArrayList<Integer>(); // the list of nodes to evaluate
        open.add(startSquare);
        ArrayList<Integer> closed = new ArrayList<Integer>(); // the list of nodes already evaluated

        g_scores[startSquare] = 0;
        f_scores[startSquare] = _heuristic(startSquare, endSquare);

        while (open.size() > 0) {
            System.out.println("Starting a loop");
            int current = _findLowestF(open, f_scores);
            System.out.println("Looking at square " + current);

            if (current == endSquare) {
                System.out.println("Made it to dest");
                return _cacheAndReturnNextNode(cameFrom, startSquare, endSquare);
            }

            System.out.println("Removing from open");
            open.remove(new Integer(current));
            System.out.println("Adding to closed");
            closed.add(new Integer(current));

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
                        open.add(neighbor);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Given a node number, returns the node number of it's non-impassible on-map neighbors
     * @param loc the node number
     * @return the non-impassible neightbors
     */
    private SmartIntArray _neighbors(int loc) {
        System.out.println("_neighbors");
        Timer.StartTimer();
        SmartIntArray neighbors = new SmartIntArray();
        int x = loc / numCoarseSquaresY;
        int y = loc % numCoarseSquaresY;

        System.out.println("Looking at neighbors for " + loc);
        // if we are safe in the middle of the map add the surrounding 8
        if (x > 0 && y > 0 && x < numCoarseSquaresX - 2 && y < numCoarseSquaresY - 2) {
            for (int i = -1; i <= 1; i ++) {
                for (int j = -1; j <= 1; j ++) {
                    if (i!= 0 || j!=0) {
                        if (map[x + i][y + j] < BehaviorConstants.IMPASSIBLE) {
                            System.out.println("Adding " + (numCoarseSquaresY * (x + i) + (y + j)));
                            neighbors.add( numCoarseSquaresY * (x + i) + (y + j));
                        }
                    }

                }
            }
        }

        // else we have to do a safe add
        // TODO: this could be split into cases and optimised? there are 8 cases :(
        else {
            for (int i = -1; i <= 1; i ++) {
                for (int j = -1; j <= 1; j ++) {
                    if ((i!= 0 || j!=0) && x + i >= 0 && x + i < numCoarseSquaresX && y + j >= 0 && y + j < numCoarseSquaresY) {
                        if (map[x + i][y + j] < BehaviorConstants.IMPASSIBLE) {
                            System.out.println("Adding " + (numCoarseSquaresY * (x + i) + (y + j)));
                            neighbors.add( numCoarseSquaresY * (x + i) + (y + j));
                        }
                    }
                }
            }

        }
        Timer.EndTimer();
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
        System.out.println("Goal met from " + start + " to " + goal + "!!!!!!!!!!!!!!========");
        System.out.println("Cache And Return");
        Timer.StartTimer();
        boolean done = false;
        int current = cameFrom[goal];
        System.out.println("Go to node " + current);
        _printSquareMapLocationCenter(current);
        pathCache[current][goal] = goal;

        if (current == start) {
            return goal;
        }

        int previous = cameFrom[current];
        do {
            pathCache[previous][goal] = current;
            int temp = previous;
            previous = cameFrom[previous];
            current = temp;
            System.out.println("Go to node " + current);
            _printSquareMapLocationCenter(current);
        } while (previous != start);

        pathCache[start][goal] = current;
        Timer.EndTimer();

        return current;
    }


    /**
     * looks through all the node ints in open for the one with the lowest f
     * @param open
     * @param f_scores
     * @return
     */
    private static int _findLowestF(ArrayList<Integer> open, int[] f_scores) {
        // TODO: this is called a lot, optimize the sh*t out of it
        // TODO: move this up to the main method to save on bytecodes
        System.out.println("_findLowestF");
        Timer.StartTimer();
        int min = Integer.MAX_VALUE;
        int minLoc = -1;
        for (int loc : open) {
            if (f_scores[loc] < min) {
                minLoc = loc;
                min = f_scores[loc];
            }

        }
        // if minLoc = -1 here something is very wrong!
        Timer.EndTimer();
        System.out.println("Found " + minLoc + " at distance " + min);
        return minLoc;
    }


    private int _heuristic(int startNode, int goalNode) {
        // TODO: this is called a lot, optimize the sh*t out of it
        int[] start = new int[]{startNode/ numCoarseSquaresY, startNode % numCoarseSquaresY};
        int [] goal = new int[]{goalNode/ numCoarseSquaresY, goalNode % numCoarseSquaresY};
        return (int)(Math.sqrt(Math.pow(start[X] - goal[X],2) + Math.pow(start[Y] - goal[Y],2)) * minMapValue);
    }




}

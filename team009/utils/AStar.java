package team009.utils;

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
    private int maxX;
    private int maxY;
    private int numNodes;

    /**
     * AStar! Needs a map of int values for the square and the minMapValue
     * @param map
     * @param minMapValue
     */
    public AStar(int[][] map, int minMapValue) {
        this.map = map;
        this.minMapValue = minMapValue;
        maxX = map.length;
        maxY = map[0].length;
        numNodes = maxX * maxY;
        pathCache = new int[maxX][maxY];
        for (int i = maxX - 1; i >= 0; i--) {
            Arrays.fill(pathCache[i], -1);
        }
    }


    public int coordsToSquareID(int x, int y) {
        return maxY * x + y;
    }


    public int[] idToCoords(int id) {
        return new int[]{id/maxY, id % maxY};
    }


    /**
     * Returns the next square the robot should move to if attempting to to got endSquare
     * from startSquare, uses a cahce if possible
     * @param startSquare the square you're starting at
     * @param endSquare the square you're trying to get to
     * @return the next square you should move to
     */
    public int getNextSquare(int startSquare, int endSquare) {
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
            int current = _findLowestF(open, f_scores);

            if (current == endSquare) {
                return _cacheAndReturnNextNode(cameFrom, startSquare, endSquare);
            }

            open.remove(current);
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
                        open.add(neighbor);
                    }
                }
            }
        }
        return -1;
    }

    private SmartIntArray _neighbors(int loc) {
        SmartIntArray neighbors = new SmartIntArray();
        int x = loc / maxY;
        int y = loc % maxY;

        // if we are safe in the middle of the map add the surrounding 8
        if (x > 0 && y > 0 && x < maxX - 2 && y < maxY - 2) {
            for (int i = -1; i <= 1; i ++) {
                for (int j = -1; j <= 1; j ++) {
                    if (i!= 0 || j!=0) {
                        if (map[maxY * (x + i)][y+j] < BehaviorConstants.IMPASSIBLE) {
                            neighbors.add( maxY * (x + i) + (y + j));
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
                    if ((i!= 0 || j!=0) && x + i >= 0 && x + i < maxX && y + j >= 0 && y + j < maxY) {
                        neighbors.add( maxY * (x + i) + (y + j));
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
        if (Math.abs(current - neighbor) <= maxY) {
            // the squares are orthagonal
            return map[neighbor/maxY][neighbor % maxY];
        } else {
            // the squares are diagonal
            return (int)(SQRT_2 *  map[neighbor/maxY][neighbor % maxY]);
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
        boolean done = false;
        int current = cameFrom[goal];
        pathCache[current][goal] = goal;

        if (current == goal) {
            return goal;
        }

        int previous = cameFrom[current];
        do {
            pathCache[previous][goal] = current;
            int temp = previous;
            previous = cameFrom[previous];
            current = temp;
        } while (previous != goal);

        return current;
    }


    private static int _findLowestF(ArrayList<Integer> open, int[] f_scores) {
        // TODO: this is called a lot, optimize the sh*t out of it
        // TODO: move this up to the main method to save on bytecodes
        int min = Integer.MAX_VALUE;
        int minLoc = -1;
        for (int loc : open) {
            if (f_scores[loc] < min) {
                minLoc = loc;
                min = f_scores[loc];
            }

        }
        // if minLoc = -1 here something is very wrong!
        return minLoc;
    }


    private int _heuristic(int startNode, int goalNode) {
        // TODO: this is called a lot, optimize the sh*t out of it
        int[] start = new int[]{startNode/maxY, startNode % maxY};
        int [] goal = new int[]{goalNode/maxY, goalNode % maxY};
        return (int)(Math.sqrt(Math.pow(start[X] - goal[X],2) + Math.pow(start[Y] - goal[Y],2)) * minMapValue);
    }




}

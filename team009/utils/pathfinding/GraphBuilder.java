/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team009.utils.pathfinding;

import java.util.Arrays;
import team009.utils.pathfinding.DeathStar;

/**
 *
 * @author alexhuleatt
 */
public class GraphBuilder {

    private final Point[] waypoints;
    private int waypoint_index;

    private final Point[] obstacles;
    private int obstacle_index;

    private int[][] adjacency_matrix;

    private int last_object_index;

    private final int length;
    private final int height;

    public GraphBuilder(int length, int height) {
        this.length = length;
        this.height = height;
        waypoints = new Point[200];
        waypoint_index = 0;

        obstacles = new Point[200];
        obstacle_index = 0;

        last_object_index = 0;
    }

    /**
     * getIndex(getWaypoint(x)) produces x if x is a valid waypoint, the
     * opposite is true.
     *
     * @param p the position of the point
     * @return The index of the given waypoint in the waypoints array.
     */
    public int getIndex(Point p) {
        for (int i = 0; i < waypoint_index; i++) {
            if (p.equals(waypoints[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds an obstacle to be later evaluated.
     *
     * @param p the position of the obstacle
     */
    public void addObstacle(Point p) {
        if (!isValid(p.x, p.y)) {
            return;
        }
        obstacles[obstacle_index] = p;
        obstacle_index++;
    }

    /**
     * @param a the index of the first waypoint to check
     * @param b the index of the second waypoint to check
     * @return true if the waypoint a is visible to waypoint b, false otherwise.
     */
    public boolean isVisible(int a, int b) {
        return isVisible(waypoints[a], waypoints[b]);
    }

    public boolean isVisible(Point p1, Point p2) {
        int maxX = Math.max(p1.x, p2.x);
        int minX = Math.min(p1.x, p2.x);
        int maxY = Math.max(p1.y, p2.y);
        int minY = Math.min(p1.y, p2.y);
        if (Math.abs(p1.x - p2.x) <= 1 && Math.abs(p1.y - p2.y) <= 1) {
            return true; //handle inner corner cases
        }
        for (int i = 0; i < waypoint_index; i++) {
            if (!waypoints[i].equals(p1) && !waypoints[i].equals(p2)) {
                Point p3 = waypoints[i];
                if (p3.x >= minX && p3.x <= maxX && p3.y >= minY && p3.y <= maxY) {
                    return false;
                }
            }
        }
        for (int i = 0; i < obstacle_index; i++) {
            Point p3 = obstacles[i];
            if (p3.x >= minX && p3.x <= maxX && p3.y >= minY && p3.y <= maxY) {
                return false;
            }
        }
        return true;
    }

    private int manhattan(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private int manhattan(Point p1, int x, int y) {
        return Math.abs(p1.x - x) + Math.abs(p1.y - y);
    }

    /**
     * Resets where to begin evaluating obstacles, such that all obstacles are
     * evaluated for waypoints.
     */
    public void reset() {
        last_object_index = 0;
        waypoint_index = 0;
    }

    public boolean isInsideCorner(int x, int y, int j, int k, int[][] map) {
        return ((isValid(x + j + k, y + j + k) && isValid(x + k, y + j) && map[x + j + k][y + j + k] == 1 && map[x+k][y+j] != 1) || (isValid(x + (j - k), y + (k - j)) && isValid(x-k, y-j) && map[x + (j - k)][y + (k - j)] == 1 && map[x-k][y-j] != 1));
    }

    /**
     * Checks all newly added obstacles for waypoints, adds any found, then
     * rebuilds the adjacency matrix. You *must* ensure that you *only* update
     * when an entire contiguous block of obstacles has been fully explored.
     * Else, visibility checking will fail. If you want to be able to add
     * objects incrementally and update simultaneously, use the reset() method
     * before updating.
     */
    public void buildMatrix() {
        int[][] map = new int[length][height];
        for (int i = last_object_index; i < obstacle_index; i++) {
            map[obstacles[i].x][obstacles[i].y] = 1;
        }
        for (int i = last_object_index; i < obstacle_index; i++) {
            int x = obstacles[i].x;
            int y = obstacles[i].y;
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (isValid(x + j, y + k) && map[x + j][y + k] == 0) {
                        map[x + j][y + k] = 2;
                        if (Math.abs(j) == 1 && Math.abs(k) == 1 && map[x + j][y] != 1 && map[x][y + k] != 1 && map[x + j][y + k] != 1) {
                            waypoints[waypoint_index] = new Point(x + j, y + k);
                            waypoint_index++;
                        } else if ((Math.abs(j) == 1 || Math.abs(k) == 1) && !(Math.abs(j) == 1 && Math.abs(k) == 1)) {
                            if (isInsideCorner(x, y, j, k, map)) {
                                waypoints[waypoint_index] = new Point(x + j, y + k);
                                waypoint_index++;
                            }
                        }
                    }
                }
            }
        }
        adjacency_matrix = new int[waypoint_index + 2][waypoint_index + 2];
        for (int i = 0; i < waypoint_index; i++) {
            for (int j = i + 1; j < waypoint_index; j++) {
                if (isVisible(i, j)) {
                    int distance = manhattan(waypoints[i], waypoints[j]);
                    adjacency_matrix[i][j] = distance;
                    adjacency_matrix[j][i] = distance;

                }
            }
        }
        last_object_index = obstacle_index;
    }

    private boolean isValid(int x, int y) {
        return (x < length && x >= 0 && y < height && y >= 0);
    }

    /**
     *
     * @return The most recently built adjacency matrix.
     */
    public int[][] getMatrix() {
        return adjacency_matrix;
    }

    /**
     *
     * @return All assigned waypoints. The length does not represent the number
     * of waypoints.
     */
    public Point[] getWayPoints() {
        return waypoints;
    }

    /**
     *
     * @return The number of found waypoints.
     */
    public int num_waypoints() {
        return waypoint_index;
    }

    /**
     * getIndex(getWaypoint(x)) produces x if x is a valid waypoint, the
     * opposite is true.
     *
     * @param val The index of the waypoint in the waypoints array.
     * @return The position of the waypoint.
     */
    public Point getWaypoint(int val) {
        return waypoints[val];
    }

    public Point[] getPath(Point start, Point finish) {
        if(isVisible(start, finish)) {
            return new Point[] { finish };
        }

        int distance;
        for (int i = 0; i < waypoint_index; i++) {
            if (isVisible(start, waypoints[i])) {
                distance = manhattan(start, waypoints[i]);
                adjacency_matrix[i][waypoint_index] = distance;
                adjacency_matrix[waypoint_index][i] = distance;
            }
        }
        for (int i = 0; i < waypoint_index; i++) {
            if (isVisible(finish, waypoints[i])) {
                distance = manhattan(finish, waypoints[i]);
                adjacency_matrix[i][waypoint_index + 1] = distance;
                adjacency_matrix[waypoint_index + 1][i] = distance;
            }
        }
        int[] path = DeathStar.findPath(adjacency_matrix, waypoint_index, waypoint_index + 1);
        if (path == null) {
            return null;
        }
        Point[] final_path = new Point[path.length];
        for (int i = 0; i < path.length; i++) {
            final_path[i] = waypoints[path[i]];
        }
        final_path[path.length - 1] = finish;
        return final_path;
    }

    public static void main(String[] args) {
        GraphBuilder g = new GraphBuilder(20, 20);
        g.addObstacle(new Point(10, 11));
        g.addObstacle(new Point(10, 12));
        g.addObstacle(new Point(10, 13));
        g.addObstacle(new Point(10, 14));

        g.addObstacle(new Point(11, 15));
        g.addObstacle(new Point(11, 16));
        g.addObstacle(new Point(11, 17));
        g.addObstacle(new Point(11, 18));
        g.addObstacle(new Point(11, 19));

        g.addObstacle(new Point(5, 3));
        g.addObstacle(new Point(5, 4));
        g.addObstacle(new Point(5, 5));
        g.addObstacle(new Point(4, 5));

        g.addObstacle(new Point(7, 8));

        g.buildMatrix();
        System.out.println(Arrays.toString(g.getPath(new Point(0, 0), new Point(19, 19))));

    }

}

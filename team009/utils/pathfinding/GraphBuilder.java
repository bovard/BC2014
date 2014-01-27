
package team009.utils.pathfinding;

import team009.utils.Timer;

import java.util.Arrays;

/**
 * The backbone, arms, legs, and spleen of the pathfinder. This is responsible
 * for constructing the adjacency matrix used to construct the path. Is super
 * sexy
 *
 * @author alexhuleatt
 */
public class GraphBuilder {

    private final Point[] waypoints;
    private int waypoint_index;

    private final Point[] obstacles;
    private int obstacle_index;

    private final boolean[] insideCorners;

    private int[][] adjacency_matrix;

    private int last_object_index;

    private final int length;
    private final int height;

    private final int[][] map;

    public static int MAX_OBSTACLES = 1000;
    public static int MAX_WAYPOINTS = 800;

    public GraphBuilder(int length, int height) {
        this.length = length;
        this.height = height;
        waypoints = new Point[MAX_WAYPOINTS];
        waypoint_index = 0;

        obstacles = new Point[MAX_OBSTACLES];
        obstacle_index = 0;

        insideCorners = new boolean[MAX_WAYPOINTS];

        map = new int[length][height];

        last_object_index = 0;
    }

    /**
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
        map[p.x][p.y] = 1;
        obstacles[obstacle_index] = p;
        obstacle_index++;
    }

    /**
     * @param a the index of the first waypoint to check
     * @param b the index of the second waypoint to check
     * @return true if the waypoint a is visible to waypoint b, false otherwise.
     */
    private boolean isVisible(int a, int b) {
        return isVisible(waypoints[a], waypoints[b]);
    }

    /**
     * Checks to see if two points are visible to each other. Uses a rectangle
     * bounded by the two points to determine visibility. It is guaranteed that
     * no object exists on any path equal to or less than the length of the
     * Manhattan distance between the two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return true if they are visible to each other, false if they might not
     * be.
     */
    private boolean isVisible(Point p1, Point p2) {
        double currentX = p1.x;
        double currentY = p1.y;
        int x_slope = (p2.x - p1.x);

        if(x_slope != 0) {
            int xDelta = x_slope > 0 ? 1 : -1;
            double y_slope = (p2.y - currentY) / Math.abs(x_slope);

            int curX = (int)currentX;
            int curY = (int)currentY;
            while (curX != p2.x) {
                if (map[curX][curY] == 1) {
                    return false;
                }

                curX += xDelta;
                currentY += y_slope;
                curY = (int)currentY;
            }
        } else {
            int curY = (int)currentY;
            int yDelta = p2.y - currentY > 0 ? 1 : -1;
            while (curY != p2.y) {
                if (map[p1.x][curY] == 1) {
                    return false;
                }
                curY += yDelta;
            }
        }
        return true;
    }

    private double euclidean(Point p1, Point p2) {return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));}

    /**
     * Finds the Manhattan distance between the two given points.
     *
     * @param p1 first coordinate
     * @param p2 second coordinate
     * @return the Manhattan distance between the points.
     */
    private int manhattan(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
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
        for (int i = last_object_index; i < obstacle_index; i++) {
            int x = obstacles[i].x;
            int y = obstacles[i].y;
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (isValid(x + j, y + k) && map[x + j][y + k] == 0) {
                        if (isOutsideCorner(x + j, y + k)) {
                            map[x + j][y + k] = 2;
                            waypoints[waypoint_index] = new Point(x + j, y + k);
                            waypoint_index++;
                        } else if (isInsideCorner(x + j, y + k)) {
                            map[x + j][y + k] = 2;
                            insideCorners[waypoint_index] = true;
                            waypoints[waypoint_index] = new Point(x + j, y + k);
                            waypoint_index++;
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

    private boolean isOutsideCorner(int x, int y) {
        if (isValid(x - 1, y - 1) && map[x - 1][y - 1] == 1) {
            if (isValid(x - 1, y) && map[x - 1][y] != 1 && isValid(x, y - 1) && map[x][y - 1] != 1) {
                return true;
            }
        }
        if (isValid(x + 1, y - 1) && map[x + 1][y - 1] == 1) {
            if (isValid(x + 1, y) && map[x + 1][y] != 1 && isValid(x, y - 1) && map[x][y - 1] != 1) {
                return true;
            }
        }
        if (isValid(x + 1, y + 1) && map[x + 1][y + 1] == 1) {
            if (isValid(x + 1, y) && map[x + 1][y] != 1 && isValid(x, y + 1) && map[x][y + 1] != 1) {
                return true;
            }
        }
        if (isValid(x - 1, y + 1) && map[x - 1][y + 1] == 1) {
            if (isValid(x - 1, y) && map[x - 1][y] != 1 && isValid(x, y + 1) && map[x][y + 1] != 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isInsideCorner(int x, int y) {
        if (isValid(x, y - 1) && map[x][y - 1] == 1) {
            if (isValid(x + 1, y) && map[x + 1][y] == 1 && map[x + 1][y - 1] != 1) {
                return true;
            }
            if (isValid(x - 1, y) && map[x - 1][y] == 1 && map[x - 1][y - 1] != 1) {
                return true;
            }
        }
        if (isValid(x, y + 1) && map[x][y + 1] == 1) {
            if (isValid(x + 1, y) && map[x + 1][y] == 1 && map[x + 1][y + 1] != 1) {
                return true;
            }
            if (isValid(x - 1, y) && map[x - 1][y] == 1 && map[x - 1][y + 1] != 1) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the coordinates are inside the boundaries of the map,
     * else false
     */
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

    /**
     * Given two points, finds a near-optimal path between them using the
     * already-made adjacency matrix.
     *
     * @param start the initial position
     * @param finish the desired ending location
     * @return a list of waypoints describing where to go. From each waypoint,
     * it is guaranteed that the next is visible.
     */
    public Point[] getPath(Point start, Point finish) {
        Timer.StartTimer();
        int distance;
        waypoints[waypoint_index] = start;
        for (int i = 0; i < waypoint_index; i++) { //Place the start into the adjacency matrix
            if (isVisible(start, waypoints[i])) { //Check which waypoints it can see
                distance = manhattan(start, waypoints[i]); //if it can see it, measure the distance
                adjacency_matrix[i][waypoint_index] = distance; //place it into the matrix
                adjacency_matrix[waypoint_index][i] = distance; //Retain symmetry
            } else {
                adjacency_matrix[i][waypoint_index] = 0; //place it into the matrix
                adjacency_matrix[waypoint_index][i] = 0; //Retain symmetry
            }
        }
        Timer.EndTimer();

        Timer.StartTimer();
        waypoints[waypoint_index + 1] = finish;
        for (int i = 0; i < waypoint_index + 1; i++) { //Place the finish into the adjacency matrix
            if (isVisible(finish, waypoints[i])) { //check visiblity
                distance = manhattan(finish, waypoints[i]); //measure distance
                adjacency_matrix[i][waypoint_index + 1] = distance; //place into matrix
                adjacency_matrix[waypoint_index + 1][i] = distance; //retain symmetry
            } else {
                adjacency_matrix[i][waypoint_index + 1] = 0; //place it into the matrix
                adjacency_matrix[waypoint_index + 1][i] = 0; //Retain symmetry
            }
        }

        int[] path = DeathStar.findPath(adjacency_matrix, waypoint_index, waypoint_index + 1); //find the path
        if (path == null) { //if null, return null
            return null;
        }
        Point[] final_path = new Point[path.length]; //convert the path from indices to positions
        for (int i = 0; i < path.length; i++) {
            final_path[i] = waypoints[path[i]];
        }
        final_path[path.length - 1] = finish;
        Timer.EndTimer();

        return final_path;
    }

    public static void main(String[] args) {
        GraphBuilder g = new GraphBuilder(20, 20);
        Point[] obstacles = new Point[]{new Point(5, 5), new Point(6, 5), new Point(5, 6), new Point(6, 6), new Point(3, 10), new Point(4, 10), new Point(5, 10), new Point(6, 10)};
        for (Point p : obstacles) {
            g.addObstacle(p);
        }
        g.buildMatrix();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Point temp = new Point(j, i);
                if (Arrays.asList(obstacles).contains(temp)) {
                    System.out.print(2 + " ");
                } else {
                    System.out.print(((g.isVisible(new Point(19, 19), new Point(j, i))) ? 1 : 0) + " ");
                }
            }
            System.out.println();
        }
        System.out.println(Arrays.toString(g.getPath(new Point(0, 0), new Point(19, 19))));

    }

}
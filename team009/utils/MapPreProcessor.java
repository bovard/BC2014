package team009.utils;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.TerrainTile;
import team009.BehaviorConstants;
import team009.hq.HQ;

public class MapPreProcessor {
    public int[][] map;
    public int[][] coarseMap;
    public double[][] milks;
    public boolean finished = false;
    public int width;
    public int height;
    public int coarseWidth;
    public int coarseHeight;
    public int coarseDivisor = COARSE_TILE_SIZE;
    public int minValue = Integer.MAX_VALUE;

    private int x = 0;
    private RobotController rc;
    public MapPreProcessor(HQ hq) {
        width = hq.info.width;
        height = hq.info.height;
        rc = hq.rc;
        map = new int[width][height];
        if (width * height > 2000) {
            coarseDivisor = COARSE_TILE_SIZE_LARGE;
        }

        coarseWidth = (int)Math.ceil(width / (1.0 * coarseDivisor));
        coarseHeight = (int)Math.ceil(height / (1.0 * coarseDivisor));

        System.out.println("Width: " + width + ", " + height + " :: " + coarseWidth + ", " + coarseHeight);
        coarseMap = new int[coarseDivisor][coarseDivisor];
    }

    public void calc() {
        if (finished) {
            return;
        }

        int coarseDivisor = this.coarseDivisor;
        int k = 0;
        int rounds = Timer.GetRounds((int)(coarseDivisor * 40), 200);
        int[][] map = this.map;
        int[][] coarseMap = this.coarseMap;
        int x = this.x;
        int VOID = this.VOID;
        int NORMAL = this.NORMAL;
        int ROAD = this.ROAD;
        int coarseWidth = this.coarseWidth;
        int coarseHeight = this.coarseHeight;
        TerrainTile tVOID = TerrainTile.VOID;
        TerrainTile tNORMAL = TerrainTile.NORMAL;
        TerrainTile tROAD = TerrainTile.ROAD;
        RobotController rc = this.rc;

        int coarseMin = Math.min(coarseHeight, coarseWidth);

        while (k < rounds && x < coarseDivisor) {
            for (; x < coarseDivisor; x++, k++) {
                for (int y = 0; y < coarseDivisor; y++) {
                    int roads = 0;
                    int normals = 0;
                    int voids = 0;
                    int xOffset = x * coarseWidth;
                    int yOffset = y * coarseHeight;

                    for (int x0 = 0; x0 < coarseWidth; x0++) {
                        for (int y0 = 0; y0 < coarseHeight; y0++) {
                            TerrainTile tile = rc.senseTerrainTile(new MapLocation(xOffset + x0, yOffset + y0));
                            if (tile == tVOID) {
                                map[xOffset + x0][yOffset + y0] = VOID;
                                voids++;
                            } else if (tile == tNORMAL) {
                                map[xOffset + x0][yOffset + y0] = NORMAL;
                                normals++;
                            } else if (tile == tROAD) {
                                map[xOffset + x0][yOffset + y0] = ROAD;
                                roads++;
                            }
                        }
                    }
                    // approximate number of rounds to move over each square
                    // normals take 2 rounds
                    // roads take 1
                    // single voids take 4 (have to take an extra turn to get around them)
                    // if the square is full of voids (voids/(coarseDivisor^2)) = 1, make the value IMPASSIBLE
                    // if the square is potentially blocked (voids/coarseDivor) = 1, make it very painful to move through
                    coarseMap[x][y] = 2*normals + roads + 4*voids + BehaviorConstants.IMPASSIBLE * (voids/(coarseWidth*coarseHeight)) + 10000 * (voids/coarseMin);
                    // need to keep track of the min value for a* to work effectively
                    if (coarseMap[x][y] < minValue) {
                        minValue = coarseMap[x][y];
                    }
                    System.out.println(coarseMap[x][y]);
                    //coarseMap[x][y] = normals + roads + voids;
                    if (voids >= coarseWidth || voids >= coarseHeight) {
                        coarseMap[x][y] += 100;
                    }
                }
            }
        }

        finished = x == coarseDivisor;
        if (finished) {
            milks = rc.senseCowGrowth();
        }
        this.x = x;
    }

    public MapLocation getCoarseMapLocation(MapLocation loc) {
        int x = loc.x / coarseWidth;
        int y = loc.y / coarseHeight;
        return new MapLocation(x + coarseWidth / 2, y + coarseHeight / 2);
    }

    public static final int DirectionToInt(Direction dir) {
        if (dir == Direction.NORTH) {
            return 0;
        } else if (dir == Direction.NORTH_EAST) {
            return 1;
        } else if (dir == Direction.EAST) {
            return 2;
        } else if (dir == Direction.SOUTH_EAST) {
            return 3;
        } else if (dir == Direction.SOUTH) {
            return 4;
        } else if (dir == Direction.SOUTH_WEST) {
            return 5;
        } else if (dir == Direction.WEST) {
            return 6;
        } else if (dir == Direction.NORTH_WEST) {
            return 7;
        }
        return -1;
    }

    public static final int COARSE_TILE_SIZE = 5;
    public static final int COARSE_TILE_SIZE_LARGE = 8;
    public static final int VOID = 3;
    public static final int NORMAL = 2;
    public static final int ROAD = 1;
}

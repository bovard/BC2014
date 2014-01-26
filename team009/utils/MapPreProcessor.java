package team009.utils;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.TerrainTile;
import team009.robot.hq.HQ;

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
                    coarseMap[x][y] = (int)(normals - Math.pow(roads, 2) + voids > 0 ? Math.pow(2, voids + 1) : 0);
                    if (coarseMap[x][y] < 0) {
                        coarseMap[x][y] = 0;
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

    public static final int COARSE_TILE_SIZE = 10;
    public static final int COARSE_TILE_SIZE_LARGE = 20;
    public static final int VOID = 2;
    public static final int NORMAL = 1;
    public static final int ROAD = 0;
}

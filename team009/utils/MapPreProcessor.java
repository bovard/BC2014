package team009.utils;

import battlecode.common.*;
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
    public int coarseTilesW;
    public int coarseTilesH;
    public int minValue = Integer.MAX_VALUE;

    private int x = 0;
    private RobotController rc;
    public MapPreProcessor(HQ hq) {
        width = hq.info.width;
        height = hq.info.height;
        rc = hq.rc;
        map = new int[width][height];
        if (width * height > 5000) {
            coarseWidth = 5;
            coarseHeight = 5;
        } else if (width * height > 2000) {
            coarseWidth = 4;
            coarseHeight = 4;
        } else {
            coarseWidth = 3;
            coarseHeight = 3;
        }

        coarseTilesW = width / coarseWidth + (width % coarseWidth == 0 ? 0 : 1);
        coarseTilesH = height / coarseHeight + (height % coarseHeight == 0 ? 0 : 1);
        coarseMap = new int[coarseTilesW][coarseTilesH];
    }

    public void calc() {
        if (finished) {
            return;
        }

        int[][] map = this.map;
        int[][] coarseMap = this.coarseMap;
        int x = this.x;
        int VOID = this.VOID;
        int NORMAL = this.NORMAL;
        int ROAD = this.ROAD;
        int coarseWidth = this.coarseWidth;
        int coarseHeight = this.coarseHeight;
        int coarseTilesW = this.coarseTilesW;
        int coarseTilesH = this.coarseTilesH;
        TerrainTile tVOID = TerrainTile.VOID;
        TerrainTile tNORMAL = TerrainTile.NORMAL;
        TerrainTile tROAD = TerrainTile.ROAD;
        TerrainTile tOFF_MAP = TerrainTile.OFF_MAP;
        RobotController rc = this.rc;

        while (Clock.getBytecodesLeft() > 400 && x < coarseTilesW) {
            for (int y = 0; y < coarseTilesH; y++) {
                int roads = 0;
                int normals = 0;
                int voids = 0;
                int offMaps = 0;
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
                        } else if (tile == tOFF_MAP) {
                            offMaps++;
                        }
                    }
                }
                // approximate number of rounds to move over each square
                // normals take 2 rounds
                // roads take 1
                // single voids take 4 (have to take an extra turn to get around them)
                // if the square is full of voids (voids/(coarseDivisor^2)) = 1, make the value IMPASSIBLE
                // if the square is potentially blocked (voids/coarseDivor) = 1, make it very painful to move through
                // TODO: Bovard don't write stupid looking code
                // TODO: Michael: your face
                coarseMap[x][y] = 2*normals + roads + 4*voids + 3*offMaps + BehaviorConstants.IMPASSIBLE * (voids/(coarseWidth*coarseWidth)) + 10000 * (voids/coarseWidth);
                // need to keep track of the min value for a* to work effectively
                if (coarseMap[x][y] < minValue) {
                    minValue = coarseMap[x][y];
                }
                //coarseMap[x][y] = normals + roads + voids;
                if (voids >= coarseWidth || voids >= coarseHeight) {
                    coarseMap[x][y] += 100;
                }
            }
            x++;
        }

        finished = x == coarseTilesW;
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

    public static final int VOID = 3;
    public static final int NORMAL = 2;
    public static final int ROAD = 1;
}

package team009.utils;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.TerrainTile;
import team009.robot.hq.HQ;

public class MapPreProcessor {
    public int[][] map;
    public double[][] milks;
    public boolean finished = false;
    public int width;
    public int height;

    private int i = 0;
    private RobotController rc;
    public MapPreProcessor(HQ hq) {
        width = hq.info.width;
        height = hq.info.height;
        rc = hq.rc;
        map = new int[width][height];
    }

    public void calc() {
        if (finished) {
            return;
        }

        int k = 0;
        int rounds = Timer.GetRounds(map[0].length * 15);
        int width = this.width; // saves 1 byte code doing local scope as opposed to class scope
        int[][] map = this.map;
        int i = this.i;
        RobotController rc = this.rc;

        while (k < rounds && i < width) {
            for (; i < width; i++, k++) {
                for (int j = 0; j < height; j++) {
                    map[i][j] = rc.senseTerrainTile(new MapLocation(i, j)) == TerrainTile.VOID ? 1 : 0;
                }
            }
        }

        finished = i == width;
        if (finished) {
            milks = rc.senseCowGrowth();
        }

        this.i = i;
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
}

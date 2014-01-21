package team009.utils;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.TerrainTile;
import team009.robot.hq.HQ;

public class MapPreProcessor {
    public int[][] map;
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

        while (k < rounds && i < height) {
            for (;i < height; i++, k++) {
                for (int j = 0; j < width; j++) {
                    map[i][j] = rc.senseTerrainTile(new MapLocation(i, j)) == TerrainTile.VOID ? 1 : 0;
                }
            }
        }

        finished = i == height;

        this.i = i;
    }
}

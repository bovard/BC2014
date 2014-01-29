package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;
import team009.BehaviorConstants;
import team009.hq.HQ;

public class CheesePostProcess {
    private RobotController rc;
    private RobotInformation info;
    private HQ hq;
    private Direction dir = Direction.NORTH;
    private int[][] map;
    private int radius = (int)Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared) - 1;
    private double milkTotal;

    public boolean finished = false;
    public boolean cheese = false;
    public MapLocation soundTower;
    public MapLocation pasture;
    public int[] directionLengths;

    public CheesePostProcess(HQ hq) {
        rc = hq.rc;
        info = hq.info;
        this.hq = hq;
        milkTotal = 0;

        pasture = info.hq.add(info.enemyDir.rotateLeft().rotateLeft());
        soundTower = info.hq.add(info.enemyDir.rotateRight().rotateRight());
        directionLengths = new int[8];
    }

    public void calc() {
        if (finished) {
            return;
        }

        if (map == null) {
            map = hq.map.map;
        }
        int[][] map = this.map;
        int k = 0;
        int rounds = Timer.GetRounds(300);
        int width = info.width;
        int height = info.height;
        double[][] milks = hq.map.milks;

        while (k < rounds && !finished) {
            MapLocation curr = info.hq.add(dir);
            int i = 1;
            while (i < radius) {
                if (curr.x < 0 || curr.x >= width || curr.y < 0 || curr.y >= height || map[curr.x][curr.y] == 1) {
                    break;
                } else {
                    milkTotal += milks[curr.x][curr.y];
                }

                curr = curr.add(dir);
            }
            directionLengths[MapPreProcessor.DirectionToInt(dir)] = i + 1;
            dir = dir.rotateRight();
            finished = dir == Direction.NORTH;
        }

        // TODO: WE DO NOT CHEESE
        finished = true;
    }

    /**
     * Gathers the best directions from the given map location
     * @param loc
     */
    public static final int[] getBestDistances(RobotController rc, MapLocation loc) {
        Direction dir = Direction.NORTH;
        MapLocation curr = loc;
        int radius = (int)Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared) - BehaviorConstants.NOISE_TOWER_REDUCTION;
        int[] radii = new int[8];

        int j;
        for (int i = 0; i < 8; i++) {
            curr = loc;
            for (j = 0; j < radius; j++) {
                curr = curr.add(dir);
                TerrainTile tile = rc.senseTerrainTile(curr);
                if (tile == TerrainTile.OFF_MAP || tile == TerrainTile.VOID) {
                    break;
                }
            }

            radii[i] = j;
        }

        return radii;
    }
}

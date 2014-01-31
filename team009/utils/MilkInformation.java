package team009.utils;

import battlecode.common.*;
import com.sun.swing.internal.plaf.metal.resources.metal_it;
import team009.RobotInformation;
import team009.hq.HQPreprocessor;

public class MilkInformation {

    // Controls
    RobotController rc;
    RobotInformation info;
    HQPreprocessor hq;
    int i = 0;
    int w = 0;
    int h = 0;

    // The public information
    public boolean finished = false;
    public MapLocation oneBaseBestSpot = new MapLocation(0, 0);
    public double oneBaseBest = -1;

    /**
     * A milk information will parse out the map and determine the
     * "sweet" spots for milking
     */
    public MilkInformation(HQPreprocessor hq) {
        this.rc = hq.rc;
        this.info = hq.info;
        this.hq = hq;
        this.i = 1;
        this.w = info.width;
        this.h = info.height;
    }

    public boolean calc() throws GameActionException {

        if (finished) {
            return true;
        }

        int i = this.i, j;

        // Now its time to sum only the bases we need.
        int k = 0;
        int[][] map = hq.map.map;
        double[][] milks = hq.map.milks;
        int wMinus1 = w - 1;
        int hMinus1 = h - 1;
        int roundsToProcess = Timer.GetRounds(hMinus1 * 120);
        int VOID = MapPreProcessor.VOID;

        while (k < roundsToProcess && i < wMinus1) {
            for (; i < wMinus1 && k < roundsToProcess; i++, k++) {
                double[] row = milks[i];
                int[] mapRow = map[i];
                for (j = 1; j < hMinus1; j++) {

                    double val = mapRow[j - 1] != VOID ? row[j - 1] : 0;
                    val += mapRow[j] != VOID ? row[j] : 0;
                    val += mapRow[j + 1] != VOID ? row[j + 1] : 0;
                    val += map[i - 1][j] != VOID ? milks[i - 1][j] : 0;
                    val += map[i + 1][j] != VOID ? milks[i + 1][j] : 0;

                    System.out.println(val + " > " + oneBaseBest);
                    if (val > oneBaseBest) {
                        oneBaseBestSpot = new MapLocation(i, j);
                        oneBaseBest = val;
                    } else if (val == oneBaseBest) {

                        // If its the same but closer, choose it
                        MapLocation loc = new MapLocation(i, j);
                        if (loc.distanceSquaredTo(info.hq) < oneBaseBestSpot.distanceSquaredTo(info.hq)) {
                            oneBaseBestSpot = new MapLocation(i, j);
                            oneBaseBest = val;
                        }
                    }
                }
            }
        }

        this.i = i;

        return finished;
    }

    public class Box {
        Box(int x, int y, int x2, int y2) {
            this.x = x;
            this.y = y;
            this.x2 = x2;
            this.y2 = y2;
            id = BoxID++;
        }
        int x, y, x2, y2, id;
        double bestMilk = 0;
        Box next;

        public MapLocation bestSpot;
    }

    private static int BoxID = 0;
}

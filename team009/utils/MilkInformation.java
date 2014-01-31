package team009.utils;

import battlecode.common.*;
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
    int cW, cH;

    // The public information
    public boolean finished = false;
    public MapLocation oneBaseBestSpot = new MapLocation(0, 0);
    public double oneBaseBest = -1;
    public int[][] coarseMap;

    /**
     * A milk information will parse out the map and determine the
     * "sweet" spots for milking
     */
    public MilkInformation(HQPreprocessor hq) {
        this.rc = hq.rc;
        this.info = hq.info;
        this.hq = hq;
        this.i = 0;
        this.w = info.width;
        this.h = info.height;

        cW = w / COARSE_SIZE;
        if (w % COARSE_SIZE != 0) {
            cW++;
        }
        cH = h / COARSE_SIZE;
        if (h % COARSE_SIZE != 0) {
            cH++;
        }
        coarseMap = new int[cW][cH];
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
        int roundsToProcess = Timer.GetRounds(32 * COARSE_SIZE * COARSE_SIZE);

        while (k < roundsToProcess && i < cW) {
            Timer.StartTimer();
            for (j = 0; j < cH && k < roundsToProcess; j++, k++) {
                int baseX = i * COARSE_SIZE;
                int baseY = j * COARSE_SIZE;

                double val = 0;
                for (int x1 = baseX, xEnd = x1 + COARSE_SIZE; x1 < xEnd; x1++) {
                    for (int y1 = baseY, yEnd = y1 + COARSE_SIZE; y1 < yEnd; y1++) {
                        if (x1 < w && y1 < h) {
                            val += milks[x1][y1];
                        }
                    }
                }

                coarseMap[i][j] = (int)val;
                boolean done = false;
                MapLocation center = new MapLocation(baseX + COARSE_SIZE / 2, baseY + COARSE_SIZE / 2);
                int distConsiderd = (int)Math.sqrt(center.distanceSquaredTo(info.enemyHq)) + (int)val;

                if (distConsiderd > oneBaseBest) {
                    for (int x1 = baseX + 1, xEnd = x1 + COARSE_SIZE; x1 < xEnd; x1++) {
                        for (int y1 = baseY + 1, yEnd = y1 + COARSE_SIZE; y1 < yEnd; y1++) {
                            if (x1 < w && y1 < h && (map[x1][y1] == MapPreProcessor.NORMAL || map[x1][y1] == MapPreProcessor.ROAD)) {
                                oneBaseBestSpot = new MapLocation(x1, y1);
                                oneBaseBest = distConsiderd;
                                done = true;
                                break;
                            }
                        }

                        if (done) {
                            break;
                        }
                    }
                } else if (distConsiderd == oneBaseBest) {

                    // If its the same but closer, choose it
                    MapLocation loc = new MapLocation(i, j);
                    if (loc.distanceSquaredTo(info.hq) < oneBaseBestSpot.distanceSquaredTo(info.hq)) {
                        for (int x1 = baseX + 1, xEnd = x1 + COARSE_SIZE; x1 < xEnd; x1++) {
                            for (int y1 = baseY + 1, yEnd = y1 + COARSE_SIZE; y1 < yEnd; y1++) {
                                if (x1 < w && y1 < h && (map[x1][y1] == MapPreProcessor.NORMAL || map[x1][y1] == MapPreProcessor.ROAD)) {
                                    oneBaseBestSpot = new MapLocation(x1, y1);
                                    oneBaseBest = distConsiderd;
                                    done = true;
                                    break;
                                }
                            }

                            if (done) {
                                break;
                            }
                        }
                    }
                }
            }
            i++;
            Timer.EndTimer();
        }

        this.i = i;
        finished = i == cW;
        if (finished) {
            System.out.println("BestSpot: " + oneBaseBestSpot + " : " + oneBaseBest);
        }

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
    private static final int COARSE_SIZE = 5;
}

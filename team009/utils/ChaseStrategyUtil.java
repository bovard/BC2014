package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;
import team009.robot.hq.HQ;

public class ChaseStrategyUtil {
    private RobotController rc;
    private RobotInformation info;
    private int i = 0;
    private int j = 0;
    private int x2, y2, wallCount = 0;

    public boolean finished = false;
    public boolean chase = false;

    public ChaseStrategyUtil(HQ hq) {
        this.rc = hq.rc;
        this.info = hq.info;

        i = info.width / 3;
        x2 = 2 * info.width / 3;

        j = 0;
        y2 = info.height;
    }

    public void calc() {
        int rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 75)) / 20;
        int k = 0;

        while (k < rounds) {
            for (; i < x2; i++, k++) {
                for (j = 0 ;j < y2; j++, k++) {
                    wallCount += rc.senseTerrainTile(new MapLocation(i, j)) == TerrainTile.VOID ? 1 : 0;
                }
            }
        }

        if (i == x2) {

            chase = wallCount > CHASE_STRATEGY_MINIMUM;
            finished = true;
        }
    }

    public static final int CHASE_STRATEGY_MINIMUM = 250;
}


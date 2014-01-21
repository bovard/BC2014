package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;
import team009.BehaviorConstants;
import team009.robot.hq.HQ;

public class ChaseStrategyUtil {
    private RobotController rc;
    private RobotInformation info;
    private int i = 0;
    private int add = 0;
    private int x2, y2, wallCount = 0;
    private HQ hq;

    public boolean finished = false;
    public boolean chase = false;

    public ChaseStrategyUtil(HQ hq) {
        this.rc = hq.rc;
        this.info = hq.info;

        i = info.width / 3;
        x2 = 2 * info.width / 3;

        add = info.width * info.height - BehaviorConstants.CHASE_STRATEGY_MAP_MINIMUM;
        add = add < 0 ? 0 : (int)Math.sqrt(add);

        y2 = info.height;
        this.hq = hq;
    }

    public void calc() {
        int rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 75)) / 25;
        int k = 0;
        int[][] map = hq.map.map;
        int j;

        while (k < rounds && i < x2) {
            for (; i < x2; i++, k++) {
                for (j = 0; j < y2 && k < rounds; j++, k++) {
                    wallCount += map[i][j];
                }
            }
        }

        if (i == x2) {
            chase = wallCount + add > BehaviorConstants.CHASE_STRATEGY_MINIMUM;
            finished = true;
        }
    }
}


package team009.utils;

import battlecode.common.Clock;
import battlecode.common.MapLocation;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class HQAttackUtil {
    private int nextActiveRound = 0;
    private ToySoldier soldier;

    public HQAttackUtil(ToySoldier soldier) {
        this.soldier = soldier;
    }
    public boolean isActiveDelayed() {
        return Clock.getRoundNum() + 5 < nextActiveRound;
    }

    public boolean toClose(MapLocation location) {
        return location.add(location.directionTo(soldier.info.enemyHq)).distanceSquaredTo(soldier.info.enemyHq) <= soldier.info.hqAttackRadius;
    }

    public boolean inProximity(MapLocation location) {
        return location.add(location.directionTo(soldier.info.enemyHq), 2).distanceSquaredTo(soldier.info.enemyHq) <= soldier.info.hqAttackRadius;
    }

    public void setActionDelay(double actionDelay) {
        nextActiveRound = Clock.getRoundNum() + (int)actionDelay;
    }
}

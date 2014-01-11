package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.AvoidEnemies;
import team009.bt.behaviors.hq.HealNearHQ;
import team009.robot.soldier.BaseSoldier;

public class LowHPSelector extends Selector {
    public static final int LOW_HP = 30;
    public static final int HIGH_HP = 80;
    public boolean healing = false;

    public LowHPSelector(BaseSoldier robot) {
        super(robot);
        addChild(new AvoidEnemies(robot));
        addChild(new HealNearHQ(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((BaseSoldier)robot).health < LOW_HP || healing;
    }

    @Override
    public boolean post() throws GameActionException {
        return ((BaseSoldier)robot).health > HIGH_HP;
    }

    @Override
    public void reset() throws GameActionException {
        healing = false;
    }
}

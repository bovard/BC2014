package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.combat.DumbBomber;
import team009.combat.DumbCombat;
import team009.robot.soldier.BaseSoldier;

public class SuicideBomber extends Behavior {
    BaseSoldier gs;
    public SuicideBomber(BaseSoldier robot) {
        super(robot);
        gs = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return gs.seesEnemy;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better Micro
        DumbBomber.Bomb(rc, gs.enemies, robot.currentLoc);

        return true;
    }
}

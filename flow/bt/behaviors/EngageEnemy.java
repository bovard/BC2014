package flow.bt.behaviors;

import battlecode.common.GameActionException;
import flow.combat.DumbCombat;
import flow.robot.soldier.BaseSoldier;

public class EngageEnemy extends Behavior {
    BaseSoldier gs;
    public EngageEnemy(BaseSoldier robot) {
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
        DumbCombat.Attack(rc, gs.enemies, robot.currentLoc);

        return true;
    }
}

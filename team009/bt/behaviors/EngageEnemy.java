package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.combat.Combat;
import team009.robot.soldier.BaseSoldier;

public class EngageEnemy extends Behavior {
    BaseSoldier gs;
    Combat combat;
    public EngageEnemy(BaseSoldier robot) {
        super(robot);
        gs = robot;
        combat = new Combat(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return gs.seesEnemy || combat.isMoving(robot.currentLoc);
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
        combat.attack(rc, gs.enemies, gs.currentLoc);

        return true;
    }
}

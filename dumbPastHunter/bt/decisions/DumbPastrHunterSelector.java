package dumbPastHunter.bt.decisions;

import battlecode.common.GameActionException;
import dumbPastHunter.bt.behaviors.DumbPastrHunt;
import dumbPastHunter.bt.behaviors.EngageEnemy;
import dumbPastHunter.bt.behaviors.MoveRandom;
import dumbPastHunter.robot.soldier.DumbPastrHunter;

public class DumbPastrHunterSelector extends Selector {
    public DumbPastrHunterSelector(DumbPastrHunter robot) {
        super(robot);
        addChild(new EngageEnemy(robot));
        addChild(new DumbPastrHunt(robot));
        addChild(new MoveRandom(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

}

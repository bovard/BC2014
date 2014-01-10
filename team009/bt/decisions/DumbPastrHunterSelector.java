package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.DumbPastrHunt;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveRandom;
import team009.robot.soldier.DumbPastrHunter;

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

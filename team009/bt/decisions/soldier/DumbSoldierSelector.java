package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.bt.behaviors.soldier.EngageEnemy;
import team009.bt.behaviors.soldier.MoveRandom;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;

public class DumbSoldierSelector extends Selector {

    public DumbSoldierSelector(TeamRobot robot) {
        super(robot);
        addChild(new EngageEnemy((team009.robot.soldier.BaseSoldier) robot));
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

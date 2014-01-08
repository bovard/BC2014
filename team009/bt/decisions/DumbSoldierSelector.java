package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveRandom;
import team009.robot.soldier.BaseSoldier;
import team009.robot.TeamRobot;

public class DumbSoldierSelector extends Selector {

    public DumbSoldierSelector(TeamRobot robot) {
        super(robot);
        addChild(new EngageEnemy((BaseSoldier) robot));
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

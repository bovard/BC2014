package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQAction;
import team009.bt.behaviors.hq.HQDefensive;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class DefensiveSelector extends Selector {
    HQAction action;
    public DefensiveSelector(HQ robot) {
        super(robot);
        children.add(new HQSoundTower(robot));
        children.add(new HQDefensive(robot));

        // Must be ran always
        action = new HQAction(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        boolean run = super.run();
        return action.run() || run;
    }
}

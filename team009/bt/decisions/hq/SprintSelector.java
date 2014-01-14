package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQAction;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.behaviors.hq.HQSprint;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class SprintSelector extends Selector {
    private HQAction action;
    public SprintSelector(HQ robot) {
        super(robot);
        children.add(new HQSoundTower(robot));
        children.add(new HQSprint(robot));
        action = new HQAction(robot);
        robot.runIfNotActive = true;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {
        boolean run = true;
        if (rc.isActive()) {
            run = super.run();
        }

        return action.run() || run;
    }

}

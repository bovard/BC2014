package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.behaviors.hq.HQSprint;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;

public class SprintSelector extends Selector {
    public SprintSelector(HQ robot) {
        super(robot);
        children.add(new HQSoundTower(robot));
        children.add(new HQSprint(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

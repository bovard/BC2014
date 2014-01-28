package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.HQSoundTower;
import team009.hq.bt.behaviors.HQSprint;
import team009.bt.decisions.Selector;
import team009.hq.HQ;

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

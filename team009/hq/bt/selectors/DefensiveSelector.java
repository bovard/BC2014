package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.HQDefensive;
import team009.bt.decisions.Selector;
import team009.hq.HQ;

public class DefensiveSelector extends Selector {
    public DefensiveSelector(HQ robot) {
        super(robot);
        children.add(new HQDefensive(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

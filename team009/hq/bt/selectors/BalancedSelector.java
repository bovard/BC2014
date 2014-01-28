package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.HQBalanced;
import team009.hq.bt.behaviors.HQSoundTower;
import team009.bt.decisions.Selector;
import team009.hq.HQ;

public class BalancedSelector extends Selector {
    public BalancedSelector(HQ robot) {
        super(robot);
        children.add(new HQSoundTower(robot));
        children.add(new HQBalanced(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQOffensive;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;

public class OffensiveSelector extends Selector {
    public OffensiveSelector(HQ robot) {
        super(robot);

        children.add(new HQShoot(robot));
        children.add(new HQOffensive(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

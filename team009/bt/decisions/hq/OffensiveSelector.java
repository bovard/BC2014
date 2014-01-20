package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQOffensive;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.decisions.Decision;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;

// TODO: Make this more efficient by hard coding the available states.
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

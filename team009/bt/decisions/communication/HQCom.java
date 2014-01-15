package team009.bt.decisions.communication;

import battlecode.common.GameActionException;
import team009.bt.behaviors.communication.HQDefendCom;
import team009.bt.behaviors.communication.HQStateCom;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;

public class HQCom extends Selector {
    public HQCom(HQ robot) {
        super(robot);
        addChild(new HQDefendCom(robot));
        addChild(new HQStateCom(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

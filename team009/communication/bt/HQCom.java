package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.HQWriteCom;
import team009.communication.bt.behaviors.HQStateCom;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;

public class HQCom extends Selector {
    public HQCom(HQ robot) {
        super(robot);
        addChild(new HQWriteCom(robot));
        addChild(new HQStateCom(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

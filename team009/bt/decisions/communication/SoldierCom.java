package team009.bt.decisions.communication;

import battlecode.common.GameActionException;
import team009.bt.behaviors.communication.SoldierReadCom;
import team009.bt.behaviors.communication.SoldierWriteCom;
import team009.bt.decisions.Selector;
import team009.robot.soldier.BaseSoldier;

public class SoldierCom extends Selector {

    BaseSoldier soldier;
    public SoldierCom(BaseSoldier soldier) {
        super(soldier);
        this.soldier = soldier;

        addChild(new SoldierWriteCom(soldier));
        addChild(new SoldierReadCom(soldier));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

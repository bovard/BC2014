package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.SoldierReadCom;
import team009.communication.bt.behaviors.SoldierWriteCom;
import team009.bt.decisions.Selector;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.ToySoldier;

public class SoldierCom extends Selector {

    ToySoldier soldier;
    public SoldierCom(ToySoldier soldier) {
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

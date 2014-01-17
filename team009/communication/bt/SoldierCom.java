package team009.communication.bt;

import battlecode.common.GameActionException;
import team009.communication.bt.behaviors.SoldierReadCom;
import team009.communication.bt.behaviors.SoldierWriteCom;
import team009.bt.decisions.Selector;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.ToySoldier;

public class SoldierCom extends Com {

    public SoldierCom(ToySoldier soldier) {
        super(soldier);

        write = new SoldierWriteCom(soldier);
        read = new SoldierReadCom(soldier);
    }
}

package team009.bt.behaviors.communication;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.robot.soldier.BaseSoldier;

public class SoldierReadCom extends ReadBehavior {
    BaseSoldier soldier;

    public SoldierReadCom(BaseSoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {

        // Updates the decoder with any information.
        soldier.decoder = Communicator.ReadFromGroup(rc, soldier.group);
        soldier.message += " Decoder: " + soldier.decoder.toString();
        return true;
    }
}


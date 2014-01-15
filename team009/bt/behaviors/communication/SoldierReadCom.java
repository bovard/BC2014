package team009.bt.behaviors.communication;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.robot.soldier.BaseSoldier;

public class SoldierReadCom extends WriteBehavior {
    BaseSoldier soldier;

    public SoldierReadCom(BaseSoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {

        // Updates the decoder with any information.
        if (Communicator.ReadRound(soldier.round)) {
            soldier.decoder = Communicator.ReadFromGroup(rc, soldier.group);
        }
        return true;
    }
}


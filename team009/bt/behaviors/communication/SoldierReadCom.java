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
        soldier.groupCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_SOLDIER_CHANEL);
        soldier.hqCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_HQ_CHANNEL);
        soldier.message += " Decoder: " + soldier.groupCommand.toString();
        return true;
    }
}


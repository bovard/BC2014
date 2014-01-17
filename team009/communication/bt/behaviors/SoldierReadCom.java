package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.robot.soldier.ToySoldier;
import team009.utils.Timer;

public class SoldierReadCom extends ReadBehavior {
    ToySoldier soldier;

    public SoldierReadCom(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {

        // Updates the decoder with any information.
        soldier.groupCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_SOLDIER_CHANEL);
        soldier.hqCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_HQ_CHANNEL);
        return true;
    }
}


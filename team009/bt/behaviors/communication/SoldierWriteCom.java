package team009.bt.behaviors.communication;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.robot.soldier.BaseSoldier;

public class SoldierWriteCom extends WriteBehavior {
    BaseSoldier soldier;

    public SoldierWriteCom(BaseSoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {
        // writes out any information about its environment.
        Communicator.WriteTypeAndGroup(rc, soldier.type, soldier.group);

        // If there is no decoder or no data, then write out information about the environment.
        if (soldier.seesEnemy && (soldier.decoder == null || soldier.decoder.hasData() || soldier.decoder.command == BaseSoldier.RETURN_TO_BASE)) {
            Communicator.WriteToGroup(rc, soldier.group, BaseSoldier.ATTACK_PASTURE, soldier.firstEnemy.location);
        }

        return true;
    }
}

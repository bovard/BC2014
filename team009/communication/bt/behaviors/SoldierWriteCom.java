package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.ToySoldier;

public class SoldierWriteCom extends WriteBehavior {
    ToySoldier soldier;

    public SoldierWriteCom(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {
        // writes out any information about its environment.
        Communicator.WriteTypeAndGroup(rc, soldier.type, soldier.group);

        // If there is no decoder or no data, then write out information about the environment.
        if (soldier.seesEnemySoldier || soldier.seesEnemyNoise || soldier.seesEnemyPastr) {
            MapLocation priorityLoc = _getPriorityLocation();
            if (GroupCommandDecoder.shouldCommunicate(soldier.groupCommand, priorityLoc, BaseSoldier.ATTACK)) {
                Communicator.WriteToGroup(rc, soldier.group, Communicator.GROUP_SOLDIER_CHANEL, BaseSoldier.ATTACK_PASTURE, priorityLoc);
            }
        }

        return true;
    }

    private MapLocation _getPriorityLocation() {
        if (soldier.seesEnemySoldier) {
            return soldier.enemySoldiers.get(0).location;
        }
        if (soldier.seesEnemyPastr) {
            return soldier.enemyPastrs.get(0).location;
        }
        if (soldier.seesEnemyNoise) {
            return soldier.enemyNoise.get(0).location;
        }
        return null;
    }
}

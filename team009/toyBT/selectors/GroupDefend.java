package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupDefend extends PushToLocation {
    ToySoldier soldier;

    public GroupDefend(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == soldier.DEFEND;
    }
}


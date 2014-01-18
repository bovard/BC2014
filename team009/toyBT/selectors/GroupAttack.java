package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import com.apple.laf.AquaToolTipUI;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupAttack extends PushToLocation {
    ToySoldier soldier;

    public GroupAttack(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == soldier.ATTACK;
    }
}

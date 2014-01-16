package team009.toyBT;

import battlecode.common.GameActionException;
import team009.bt.decisions.Selector;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.selectors.GroupAttack;
import team009.toyBT.selectors.GroupAttackPasture;
import team009.toyBT.selectors.GroupDefend;
import team009.toyBT.selectors.GroupReturnToBase;

public class ToySelector extends Selector {
    public ToySelector(ToySoldier robot) {
        super(robot);

        // Attack as a group
        addChild(new GroupAttack(robot));

        // Defends pasture as group
        addChild(new GroupDefend(robot));

        // Defends pasture as group
        addChild(new GroupAttackPasture(robot));

        // Defends pasture as group
        addChild(new GroupReturnToBase(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}


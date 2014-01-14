package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.DefenderBehavior;
import team009.robot.soldier.BaseSoldier;

public class DefenderSelector extends Selector {
    public DefenderSelector(BaseSoldier robot) {
        super(robot);

        // Adds group engage signals
        addChild(new GroupEngageEnemy(robot));

        // Adds in Group hq commands
        addChild(new DefenderBehavior(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

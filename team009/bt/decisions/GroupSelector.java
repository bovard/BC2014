package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.robot.soldier.BaseSoldier;

public class GroupSelector extends Selector {
    public GroupSelector(BaseSoldier robot) {
        super(robot);

        // Adds group engage signals
        addChild(new GroupEngageEnemy(robot));

        // Adds in Group hq commands
        addChild(null);
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
    }
}

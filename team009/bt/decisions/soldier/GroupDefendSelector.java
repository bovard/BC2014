package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.robot.soldier.BaseSoldier;

public class GroupDefendSelector extends AggressiveMove {

    public GroupDefendSelector(BaseSoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (gs.groupCommand != null && gs.groupCommand.command == BaseSoldier.RETURN_TO_BASE) {
            location.setDestination(gs.groupCommand.location);
        } else {
            location.setDestination(null);
        }

        return location.pre();
    }
}


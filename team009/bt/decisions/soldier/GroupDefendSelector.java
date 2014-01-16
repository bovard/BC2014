package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.robot.soldier.BaseSoldier;

public class GroupDefendSelector extends AggressiveMove {

    public GroupDefendSelector(BaseSoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (gs.decoder != null && gs.decoder.command == BaseSoldier.RETURN_TO_BASE) {
            location.setDestination(gs.decoder.location);
        } else {
            location.setDestination(null);
        }

        return location.pre();
    }
}


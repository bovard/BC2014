package flow.bt.decisions;

import battlecode.common.GameActionException;
import flow.robot.soldier.BaseSoldier;

public class GroupEngageEnemy extends AggressiveMove {

    public GroupEngageEnemy(BaseSoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (gs.decoder != null) {
            rc.setIndicatorString(1, "Check it: " + gs.seesEnemy + " || (" + (gs.decoder != null) + " && " + gs.decoder.command + " == " + BaseSoldier.ATTACK + "))");
        }
        if (gs.decoder != null && gs.decoder.command == BaseSoldier.ATTACK) {
            location.setDestination(gs.decoder.location);
            return true;
        }

        return gs.seesEnemy;
    }
}


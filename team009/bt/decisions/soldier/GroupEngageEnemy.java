package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.robot.soldier.BaseSoldier;

public class GroupEngageEnemy extends AggressiveMove {

    public GroupEngageEnemy(BaseSoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (gs.decoder != null && gs.decoder.command == BaseSoldier.ATTACK_PASTURE) {
            location.setDestination(gs.decoder.location);
            return true;
        }

        return gs.seesEnemy;
    }
}


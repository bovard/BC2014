package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupAttackPasture extends ToyMoveToLocation {
    public GroupAttackPasture(ToySoldier soldier) {
        super(soldier);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == TeamRobot.ATTACK_PASTURE;
    }
}

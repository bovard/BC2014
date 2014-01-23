package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupReturnToBase extends ToyMoveToLocation {
    public GroupReturnToBase(ToySoldier soldier) {
        super(soldier);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == TeamRobot.RETURN_TO_BASE;
    }
}


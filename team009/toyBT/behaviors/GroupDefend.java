package team009.toyBT.behaviors;

import _team0_2_4.bt.behaviors.MoveToLocation;
import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupDefend extends ToyMoveToLocation {
    public GroupDefend(ToySoldier soldier) {
        super(soldier);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == TeamRobot.DEFEND;
    }
}


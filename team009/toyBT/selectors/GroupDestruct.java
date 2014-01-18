package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupDestruct extends PushToLocation {
    ToySoldier soldier;

    public GroupDestruct(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == soldier.DESTRUCT;
    }
}


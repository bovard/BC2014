package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class GroupCapture extends PushToLocation {
    ToySoldier soldier;

    public GroupCapture(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == soldier.CAPTURE_PASTURE;
    }
}


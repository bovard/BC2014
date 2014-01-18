package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;

public class ToyHerdReturn extends ToyMoveToLocation {
    public ToyHerdReturn(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return robot.currentLoc.distanceSquaredTo(soldier.comLocation) <= 6;
    }
}

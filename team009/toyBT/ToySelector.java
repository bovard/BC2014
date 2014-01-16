package team009.toyBT;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.robot.soldier.ToySoldier;

public class ToySelector extends Node {
    public ToySelector(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        return false;
    }
}

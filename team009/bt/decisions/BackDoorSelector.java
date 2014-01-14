package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.BackDoorNoisePlant;
import team009.bt.behaviors.EngageEnemy;
import team009.robot.soldier.BackdoorNoisePlanter;

public class BackDoorSelector extends Selector {
    public BackDoorSelector(BackdoorNoisePlanter robot) {
        super(robot);
        children.add(new EngageEnemy(robot));
        children.add(new BackDoorNoisePlant(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

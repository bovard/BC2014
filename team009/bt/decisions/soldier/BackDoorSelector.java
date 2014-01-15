package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.bt.behaviors.soldier.BackDoorNoisePlant;
import team009.bt.behaviors.soldier.EngageEnemy;
import team009.bt.decisions.Selector;
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

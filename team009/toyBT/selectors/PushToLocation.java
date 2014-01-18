package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.decisions.Selector;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyEngageEnemy;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class PushToLocation extends Selector {
    ToySoldier soldier;
    ToyMoveToLocation location;
    ToyEngageEnemy engage;

    public PushToLocation(ToySoldier robot) {
        super(robot);
        soldier = robot;
        location = new ToyMoveToLocation(robot);
        engage = new ToyEngageEnemy(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return location.pre();
    }

    public boolean run() throws GameActionException {
        if (!soldier.comLocation.equals(location.currentLocation)) {
            location.setDestination(soldier.comLocation);
        }
        return engage.pre() ? engage.run() : location.run();
    }
}



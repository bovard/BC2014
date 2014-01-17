package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.decisions.Selector;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyEngageEnemy;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class PushToLocation extends Selector {
    ToySoldier gs;
    ToyMoveToLocation location;

    public PushToLocation(ToySoldier robot) {
        super(robot);
        gs = robot;
        location = new ToyMoveToLocation(robot);

        children.add(new ToyEngageEnemy(robot));
        children.add(location);
    }

    @Override
    public boolean pre() throws GameActionException {
        return location.pre();
    }
}



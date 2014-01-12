package flow.bt.decisions;

import battlecode.common.GameActionException;
import flow.bt.behaviors.EngageEnemy;
import flow.bt.behaviors.MoveToLocation;
import flow.robot.soldier.BaseSoldier;

public class AggressiveMove extends Selector {
    BaseSoldier gs;
    MoveToLocation location;

    public AggressiveMove(BaseSoldier robot) {
        super(robot);
        gs = robot;
        location = new MoveToLocation(robot);

        children.add(new EngageEnemy(robot));
        children.add(location);
    }

    @Override
    public boolean pre() throws GameActionException {
        return location.pre();
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
    }
}


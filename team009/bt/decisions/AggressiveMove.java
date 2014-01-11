package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveToLocation;
import team009.robot.soldier.BaseSoldier;

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


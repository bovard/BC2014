package flow.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import flow.bt.behaviors.EngageEnemy;
import flow.bt.behaviors.MoveToLocation;
import flow.bt.behaviors.PastureCapture;
import flow.robot.soldier.BaseSoldier;
import flow.robot.TeamRobot;

public class PastureSelector extends Selector {

    public PastureSelector(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);

        // The children from most to least important
        addChild(new EngageEnemy((BaseSoldier)robot));
        //addChild(new ComRetreatToPasture((Herder)robot, pastureLocation));
        addChild(new MoveToLocation(robot, pastureLocation));
        addChild(new PastureCapture(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }
}

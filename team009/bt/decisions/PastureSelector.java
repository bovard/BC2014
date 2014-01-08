package team009.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveToLocation;
import team009.bt.behaviors.PastureCapture;
import team009.robot.GenericSoldier;
import team009.robot.TeamRobot;

public class PastureSelector extends Selector {

    public PastureSelector(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);

        // The children from most to least important
        addChild(new EngageEnemy((GenericSoldier)robot));
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

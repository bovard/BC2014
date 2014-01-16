package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.soldier.EngageEnemy;
import team009.bt.behaviors.soldier.MoveToLocation;
import team009.bt.behaviors.soldier.PastureCapture;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;

public class PastureSelector extends Selector {

    public PastureSelector(TeamRobot robot, MapLocation pastureLocation) {
        super(robot);

        // The children from most to least important
        addChild(new EngageEnemy((team009.robot.soldier.BaseSoldier)robot));
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

package team009.bt.decisions;

import battlecode.common.GameActionException;
import sun.net.www.content.text.Generic;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveRandom;
import team009.bt.behaviors.MoveToLocation;
import team009.robot.GenericSoldier;
import team009.robot.TeamRobot;


public class WolfSelector extends Selector {

    public WolfSelector(TeamRobot robot) {
        super(robot);
        //detect if an enemy PASTR has spawned
        //addChild(new MoveRandom(robot));
        addChild(new EngageEnemy((GenericSoldier) robot));
        //TODO add behavior GetNextPastr


        //test goto enemy HQ location
        addChild(new MoveToLocation(robot, robot.info.enemyHq));
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

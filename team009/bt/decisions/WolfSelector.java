package team009.bt.decisions;

import battlecode.common.GameActionException;
import sun.net.www.content.text.Generic;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveRandom;
import team009.bt.behaviors.MoveToLocation;
import team009.robot.TeamRobot;
import team009.robot.soldier.Wolf;


public class WolfSelector extends Selector {

    public WolfSelector(Wolf robot) {
        super(robot);
        // heal guys
        // engage enemy
        addChild(new EngageEnemy(robot));
        // move to pastr location(s)
        // group/swarm to center
        // broadcast possible pasture locations?

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

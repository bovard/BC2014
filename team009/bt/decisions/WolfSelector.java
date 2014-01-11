package team009.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import sun.net.www.content.text.Generic;
import team009.bt.behaviors.*;
import team009.robot.TeamRobot;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.Wolf;


public class WolfSelector extends Selector {

    public WolfSelector(Wolf robot) {
        super(robot);
        // heal guys
        addChild(new LowHPRetreat((BaseSoldier)robot));
        // engage enemy
        //addChild(new SuicideBomber(robot));
        addChild(new EngageEnemy(robot));
        // move to pastr location(s)
        addChild(new WolfPastrHunt(robot));
        // group/swarm to center
        MapLocation center = new MapLocation(robot.info.width/2, robot.info.height/2);
        //TODO determine if a robot can actually goto this spot for a pastr or noise tower
        addChild(new MoveToLocation(robot, center));
        addChild(new PastureCapture(robot));
        //addChild(new NoiseTowerCapture(robot));
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

package flow.bt.decisions;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import flow.bt.behaviors.*;
import flow.robot.soldier.BaseSoldier;
import flow.robot.soldier.Wolf;

import java.util.Random;


public class WolfSelector extends Selector {
    Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    Random rand = new Random();

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
        //temp just goto the enemy HQ
        //addChild(new MoveToLocation(robot, robot.info.enemyHq));
        addChild(new PastureCapture(robot));

        //TODO after the first pastr is captured, we need to plant a noisetower to herd
        addChild(new MoveToLocation(robot, center.add(directions[rand.nextInt(8)], 2)));
        addChild(new NoiseTowerCapture(robot));
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

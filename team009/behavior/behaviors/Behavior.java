package team009.behavior.behaviors;

import java.util.Random;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import team009.behavior.Node;
import team009.robot.TeamRobot;


public abstract class Behavior extends Node {

	public Random rand;
	
	public Behavior(TeamRobot robot) {
        super(robot);
		rand = new Random();
    	rand.setSeed(Clock.getRoundNum());
	}

}

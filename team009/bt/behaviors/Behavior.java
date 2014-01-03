package team009.bt.behaviors;

import java.util.Random;

import battlecode.common.Clock;
import team009.bt.Node;
import team009.robot.TeamRobot;


public abstract class Behavior extends Node {

	public Random rand;
	
	public Behavior(TeamRobot robot) {
        super(robot);
		rand = new Random();
    	rand.setSeed(Clock.getRoundNum());
	}

}

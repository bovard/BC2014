package dumbPastHunter.bt.behaviors;

import java.util.Random;

import dumbPastHunter.bt.Node;
import dumbPastHunter.robot.TeamRobot;


public abstract class Behavior extends Node {

	public Random rand;

	public Behavior(TeamRobot robot) {
        super(robot);
	}

}

package flow.bt.behaviors;

import java.util.Random;

import flow.bt.Node;
import flow.robot.TeamRobot;


public abstract class Behavior extends Node {

	public Random rand;

	public Behavior(TeamRobot robot) {
        super(robot);
	}

}

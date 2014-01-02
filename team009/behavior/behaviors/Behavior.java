package team009.behavior.behaviors;

import java.util.Random;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import team009.behavior.Node;


public abstract class Behavior extends Node {

	public Random rand;
	
	public Behavior() {
		rand = new Random();
    	rand.setSeed(Clock.getRoundNum());
	}
	
	/**
	 * Called when starting a behavior. There may be one-time things you will have
	 * to do such as computing a path. Default to empty
	 */
	public void start() throws GameActionException {
		
	}
	
	/**
	 * Called when stopping a behavior. There may be one-time things you want to do 
	 * when exiting. Default to empty
	 */
	public void stop() throws GameActionException {

	}

	/**
	 * Executes the node
     * Returns true if the behavior has completed, false otherwise
	 */
	public abstract boolean run() throws GameActionException;
}

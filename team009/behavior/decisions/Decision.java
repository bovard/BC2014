package team009.behavior.decisions;

import battlecode.common.GameActionException;
import team009.behavior.Node;

public abstract class Decision extends Node {

	/**
	 * This will choose the appropriate behavior to run
	 * In a selector it will choose a random function.
	 * In a sequence it will choose the one in sequence.
     * The sequence will be called again when the first one succeeds (How to know this?)
	 * @return the Node to run
	 */
	public abstract Node select() throws GameActionException;
}

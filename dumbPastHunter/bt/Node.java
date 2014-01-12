package dumbPastHunter.bt;

import java.util.ArrayList;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import dumbPastHunter.robot.TeamRobot;

/**
 * The base Node.
 * Most every node has children and a parent
 * Every Node can be run and has pre-conditions
 */
public abstract class Node {
	public ArrayList<Node> children;
    protected TeamRobot robot;
    protected RobotController rc;

	public Node(TeamRobot robot) {
        this.robot = robot;
        this.rc = robot.rc;
		children = new ArrayList<Node>();
	}

    public void addChild(Node node) {
        this.children.add(node);
    }

	/**
	 * pre checks if the preconditions are met for a node to occur
	 *
	 * For example: A precondition for mining or de-mining is that you aren't in
	 * firing range of an enemy (you might see one though!)
	 *
	 * Note: this should only do specialized checks, most state information should
	 * be computed before pre/post is called
     *
     * Note: you'll only be at a node if the pre of the parent is met so you don't
     * need to put pres like not in combat conditions everywhere
	 *
	 * @return If the conditions are met for the node.
	 */
	public abstract boolean pre() throws GameActionException;

    /**
     * post checks to see if the node has completed successfully!
     *
     * for a behavior this may be getting to a certain square
     * for a selector this is having a child succeed
     * for a sequence this is having all child behaviors succeed
     *
     * Note: post should ALWAYS be checked before running a node
     *
     * @return if the goal condition has been met for the node
     * @throws GameActionException
     */
    public abstract boolean post() throws GameActionException;

    /**
     * should be called after a goal is met
     *
     * Note: CANNOT change pre/post conditions or bad things will happen!
     *
     * @return
     * @throws GameActionException
     */
    public abstract void reset() throws GameActionException;

    /**
     * run should run the behavior/selector/sequence returns true if an action was
     * decided (stay put, move, shoot, spawn, etc..)
     * @return
     * @throws GameActionException
     */
    public abstract boolean run() throws GameActionException;

}

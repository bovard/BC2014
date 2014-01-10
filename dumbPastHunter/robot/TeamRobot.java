package dumbPastHunter.robot;

import battlecode.common.*;
import dumbPastHunter.RobotInformation;
import dumbPastHunter.bt.Node;

import java.util.Random;

public abstract class TeamRobot {

	protected Node treeRoot;
    public MapLocation currentLoc;
    public MapLocation lastLoc;
    public double health;
    public int round;
	public RobotController rc;
	public RobotInformation info;
    public Random rand = new Random();

	public TeamRobot(RobotController rc, RobotInformation info) {
		this.rc = rc;
		this.info = info;
        currentLoc = rc.getLocation();
        lastLoc = info.hq;
        rand.setSeed(Clock.getRoundNum());
	}

	/**
	 * This will check the environment around the robot, check for messages
	 * check to see if enemies are near.
	 *
	 */
	public void environmentCheck() throws GameActionException {
        MapLocation temp = rc.getLocation();
        if (!temp.equals(currentLoc)) {
            lastLoc = currentLoc;
        }
        currentLoc = temp;
        round = Clock.getRoundNum();
        health = rc.getHealth();
    }

	/**
	 * Called at the end of a robots turn, can load things...
	 */
	public void postProcessing() throws GameActionException {}

    /**
     * Called during the constructor to load up the right bt
     * @return the root of the BT for the bot
     */
    protected abstract Node getTreeRoot();

	/**
	 * We should never return from this
	 */
	public void run() {
        while (true) {
            int round = Clock.getRoundNum();

            try {
                // at the start of the round, update with an environment check
                this.environmentCheck();

                // have the tree choose what to do
                if (rc.isActive()) {
                    treeRoot.run();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            // then postProcessing with whatever remains of our byte code
            try {
                this.postProcessing();
            } catch (Exception e) {
                System.out.println("Load error: " );
                e.printStackTrace();
            }

            // only yield if we're still on the same clock turn
            // if we aren't that means that we ended up skipping
            // our turn because we went too long
            if (round == Clock.getRoundNum()) {
                this.rc.yield();
            } else {
                System.out.println("BYTECODE LIMIT EXCEEDED!");
            }
        }

	}
}

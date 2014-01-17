package team009.robot;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;

import java.util.Random;

public abstract class TeamRobot {

    // Somtimes i wish valid identifiers could contain explanation points!
    public static final int RETURN_TO_BASE = 1;
    public static final int ATTACK_PASTURE = 2;
    public static final int DEFEND = 3;
    public static final int ATTACK = 4;
    public static final int DESTRUCT = 9;

    // Groups
    public static final int TOY_GROUP = 0;

    protected Node treeRoot;
    protected Node comRoot = null;
    public MapLocation currentLoc;
    public MapLocation lastLoc;
    public double health;
    public int round;
	public RobotController rc;
	public RobotInformation info;
    public Random rand = new Random();
    public String message;

	public TeamRobot(RobotController rc, RobotInformation info) {
        // MAKE SURE YOU INCLUDE THE FOLLOWING LINE IN YOUR IMPLEMENTATION
        // treeRoot = getTreeRoot();
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
        message = "";
    }

	/**
	 * Called at the end of a robots turn, can load things...
	 */
	public void postProcessing() throws GameActionException {
        if (message.length() > 0) {
            rc.setIndicatorString(0, message);
            message = "";
        }
    }

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

                // every turn we run the comRoot
                if (comRoot != null) {
                    comRoot.run();
                }

                // if we're active have the tree choose what to do
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

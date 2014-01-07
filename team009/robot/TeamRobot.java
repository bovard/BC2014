package team009.robot;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;

public abstract class TeamRobot {

	protected Node treeRoot;
    public MapLocation currentLoc;
	public RobotController rc;
	public RobotInformation info;
    public MapLocation targetLocation;

	public TeamRobot(RobotController rc, RobotInformation info) {
		this.rc = rc;
		this.info = info;
        this.treeRoot = getTreeRoot();
	}

	/**
	 * This will check the environment around the robot, check for messages
	 * check to see if enemies are near.
	 *
	 */
	public void environmentCheck() throws GameActionException {
        this.currentLoc = rc.getLocation();

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
                System.out.println("TeamRobot run");
                // at the start of the round, update with an environment check
                this.environmentCheck();

                // have the tree choose what to do
                treeRoot.run();

            } catch (Exception e) {
                e.printStackTrace();
            }


            // then postProcessing with whatever remains of our byte code
            try {
                this.postProcessing();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Load error: " );
                e.printStackTrace();
            }

            // only yield if we're still on the same clock turn
            // if we aren't that means that we ended up skipping
            // our turn because we went too long
            if (round == Clock.getRoundNum()) {
                this.rc.yield();
                this.rc.setIndicatorString(0, "-");
            } else {
                System.out.println("BYTECODE LIMIT EXCEEDED!");
            }
        }

	}
}

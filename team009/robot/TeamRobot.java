package team009.robot;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.behavior.Node;
import team009.communication.Communicator;

/**
 * This is the Robot Class, you'll need to extend it
 * 
 * Every Robot should have a 
 * 
 * @author bovard.tiberi
 *
 */
public abstract class TeamRobot {
	
	protected Node treeRoot;
	public RobotController rc;
	public RobotInformation info;
	public Communicator com;
	
	public TeamRobot(RobotController rc, RobotInformation info) {
		this.rc = rc;
		this.info = info;
		com = new Communicator(rc);
	}
	
	/**
	 * This will check the environment around the robot, check for messages
	 * check to see if enemies are near. 
	 * 
	 */
	public abstract void environmentCheck() throws GameActionException;
	
	/**
	 * Called at the end of a robots turn, can load things...
	 */
	public void postProcessing() throws GameActionException {};
	
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

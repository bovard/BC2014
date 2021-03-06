package team009.robot;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.communication.Communicator;
import team009.robot.soldier.ToySoldier;

import java.util.Random;

public abstract class TeamRobot {
    // the distnace at which if you are greater than you are more than one square away from the enemy
    public final static int ONE_SQUARE_AWAY_MAX = 18;

    // These are Group / HQ commands
    public static final int RETURN_TO_BASE = 1;
    public static final int ATTACK_PASTURE = 2;
    public static final int CAPTURE_PASTURE = 3;
    public static final int CAPTURE_SOUND = 4;
    public static final int DEFEND = 5;
    public static final int HQ_SURROUND = 6;
    public static final int ATTACK = 7;
    public static final int DESTRUCT = 9;

    // These are HQ -> Soldier commands
    public static final int NEED_WAY_POINT = 3;
    public static final int WAY_POINT_RECEIVED = 2;
    public static final int POSITION_OF_NOISE_TOWER = 3;
    public static final int POSITION_OF_PASTR = 4;
    public static final int REQUEST_LOCATION = 5;
    public static final int LOCATION_RESULT = 6;

    public static final int SOLDIER_GROUP = 0;
    public static final int NOISE_TOWER_GROUP = 1;
    public static final int PASTR_GROUP = 2;


    public static final int SOLDIER_TYPE_TOY_SOLDIER = 1;
    public static final int SOLDIER_TYPE_NOISE_TOWER = 2;
    public static final int SOLDIER_TYPE_PASTR = 3;
    public static final int SOLDIER_COUNT = 3;

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
    public int group;
    public int type;
    public MapLocation groupCentroid;
    public int twoWayChannel;

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

                // every turn we run the comRoot
                if (comRoot != null) {
                    comRoot.run();
                }

                // if we're active have the tree choose what to do
                if (rc.isActive()) {
                    treeRoot.run();
                }
                if (rc.isConstructing() && rc.getConstructingRounds() == 1) {
                    Communicator.WritePassComChannel(rc, twoWayChannel, rc.getConstructingType() == RobotType.NOISETOWER);
                }

            } catch (Exception e) {
                e.printStackTrace();
                String str = "Error: " + e.getMessage();
                if (this instanceof ToySoldier) {
                    str += " : " + ((ToySoldier)this).group;
                    str += " : " + ((ToySoldier)this).comLocation;
                    str += " : " + ((ToySoldier)this).comCommand;
                }
                //rc.setIndicatorString(2, str);
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

package team009;

import battlecode.common.*;

public class RobotInformation {
	public Team myTeam;
	public Team enemyTeam;
    public Robot robot;
    public RobotInfo info;
	public MapLocation hq;
	public MapLocation enemyHq;
	public MapLocation center;
	public int id;
	public int width;
	public int height;
	public int enemyHqDistance;
    public Direction enemyDir;
	public RobotController rc;

	/**
	 * Will construct a robot information. These are common operations that
	 * require bytecode execution and can be saved by storing the information.
	 *
	 * @param rc
	 */
	public RobotInformation(RobotController rc) throws GameActionException {
		myTeam = rc.getTeam();
		enemyTeam = myTeam.opponent();
        height = rc.getMapHeight();
        width = rc.getMapWidth();
        hq = rc.senseHQLocation();
        enemyHq = rc.senseEnemyHQLocation();
        enemyHqDistance = hq.distanceSquaredTo(enemyHq);
        robot = rc.getRobot();
        info = rc.senseRobotInfo(robot);
	}

}

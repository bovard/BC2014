package team009.bt.behaviors.noise;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.utils.SmartMapLocationArray;

public class CounterTower extends Behavior {

    private int distToHQ;
    private int distToEnemyHQ;
    private int lastPastr;

    public CounterTower(TeamRobot robot) {
        super(robot);
        distToHQ = robot.currentLoc.distanceSquaredTo(robot.info.hq);
        distToEnemyHQ = robot.currentLoc.distanceSquaredTo(robot.info.enemyHq);
        lastPastr = 0;
    }

    @Override
    public boolean pre() throws GameActionException {
        return distToEnemyHQ > distToHQ;
    }

    @Override
    public boolean run() throws GameActionException {
        MapLocation[] enemyPastr = robot.rc.sensePastrLocations(robot.info.enemyTeam);
        SmartMapLocationArray enemyPastrsInRange = new SmartMapLocationArray();

        // find enemy pastrs in range
        for (MapLocation e : enemyPastr) {
            if (robot.currentLoc.distanceSquaredTo(e) < RobotType.NOISETOWER.attackRadiusMaxSquared) {
                enemyPastrsInRange.add(e);
            }
        }

        if (enemyPastrsInRange.length > 0) {
            lastPastr = lastPastr % enemyPastrsInRange.length;
            robot.rc.attackSquare(enemyPastrsInRange.arr[lastPastr]);
            lastPastr++;
            return true;
        }
        return false;
    }
}

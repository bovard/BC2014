package team009.toyBT.micro;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class EngageLoneEnemyPastr extends Behavior {
    public EngageLoneEnemyPastr(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemyPastr;
    }

    @Override
    public boolean run() throws GameActionException {
        RobotInfo pastr = ((ToySoldier)robot).enemyPastrs.arr[0];
        MapLocation pastrLocation = pastr.location;
        return false;
    }
}
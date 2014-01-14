package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import team009.navigation.BugMove;
import team009.robot.soldier.BackdoorNoisePlanter;

public class BackDoorNoisePlant extends Behavior {
    private BugMove move;

    public BackDoorNoisePlant(BackdoorNoisePlanter robot) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(robot.getNextWayPoint());
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        int enemyHQ = robot.currentLoc.distanceSquaredTo(robot.info.enemyHq);
        if (enemyHQ < robot.currentLoc.distanceSquaredTo(robot.info.hq) && enemyHQ < 300) {
            rc.construct(RobotType.NOISETOWER);
            return true;
        } else if (robot.currentLoc.distanceSquaredTo(move.destination) < 26) {
            move.setDestination(((BackdoorNoisePlanter)robot).getNextWayPoint());
            move.move();
            return true;
        } else {
            move.move();
            return true;
        }
    }
}

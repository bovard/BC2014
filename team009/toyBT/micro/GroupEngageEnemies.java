package team009.toyBT.micro;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class GroupEngageEnemies extends Behavior {
    public GroupEngageEnemies(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemySoldier && ((ToySoldier)robot).allies.length > 0;
    }

    @Override
    public boolean run() throws GameActionException {
        // if we outnumber them
        if (((ToySoldier)robot).enemySoldiers.length < ((ToySoldier)robot).allies.length) {
            MapLocation nearestEnemyLoc = ((ToySoldier)robot).enemySoldiers.arr[0].location;
            int distance = ((ToySoldier) robot).currentLoc.distanceSquaredTo(nearestEnemyLoc);
            // get right outside engagement range
            if (distance > TeamRobot.ONE_SQUARE_AWAY_MAX) {
                Direction toMove = ((ToySoldier) robot).currentLoc.directionTo(nearestEnemyLoc);
                if (robot.rc.canMove(toMove)) {
                    robot.rc.move(toMove);
                    return true;
                }
                if (robot.rc.canMove(toMove.rotateLeft())) {
                    robot.rc.move(toMove.rotateLeft());
                    return true;
                }
                if (robot.rc.canMove(toMove.rotateRight())) {
                    robot.rc.move(toMove.rotateRight());
                    return true;
                }
                // sit and wait for an opening
                // TODO: this will cause us to be dumb around obstacles
                return true;
            }
            // if you are, sense how many of your fellows are just outside engagement range
            Robot[] allies = robot.rc.senseNearbyGameObjects(Robot.class, nearestEnemyLoc, TeamRobot.ONE_SQUARE_AWAY_MAX, robot.info.myTeam);
            // if you have many move in
            if (allies.length >= ((ToySoldier)robot).enemySoldiers.length) {
                Direction toMove = ((ToySoldier) robot).currentLoc.directionTo(nearestEnemyLoc);
                if (robot.rc.canMove(toMove)) {
                    robot.rc.move(toMove);
                    return true;
                }
                if (robot.rc.canMove(toMove.rotateLeft())) {
                    robot.rc.move(toMove.rotateLeft());
                    return true;
                }
                if (robot.rc.canMove(toMove.rotateRight())) {
                    robot.rc.move(toMove.rotateRight());
                    return true;
                }

            }
            // else sit there
            else {
                return true;
            }
        }

        // if they outnumber us
        // retreat!
        MapLocation nearestEnemyLoc = ((ToySoldier)robot).enemySoldiers.arr[0].location;
        Direction toMove = nearestEnemyLoc.directionTo(((ToySoldier) robot).currentLoc);
        if (robot.rc.canMove(toMove)) {
            robot.rc.move(toMove);
            return true;
        }
        if (robot.rc.canMove(toMove.rotateLeft())) {
            robot.rc.move(toMove.rotateLeft());
            return true;
        }
        if (robot.rc.canMove(toMove.rotateRight())) {
            robot.rc.move(toMove.rotateRight());
            return true;
        }


        //System.out.println("Stuck in group engage");
        return false;
    }
}

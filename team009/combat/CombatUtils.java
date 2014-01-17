package team009.combat;

import battlecode.common.*;
import team009.robot.TeamRobot;


public class CombatUtils {
    public static MapLocation findCenterOfMass(Robot[] robots, RobotController rc) throws GameActionException {
        return findCenterOfMass(getRobotInfo(robots, rc));
    }

    public static MapLocation findCenterOfMass(RobotInfo[] infos) {
        double x = 0.0;
        double y = 0.0;
        for (RobotInfo i : infos) {
            x += i.location.x;
            y += i.location.y;
        }
        return new MapLocation((int)(x/infos.length), (int)(y/infos.length));
    }

    public static MapLocation findCenterOfMass(MapLocation[] locs) {
        double x = 0.0;
        double y = 0.0;
        for (MapLocation i : locs) {
            x += i.x;
            y += i.y;
        }
        return new MapLocation((int)(x/locs.length), (int)(y/locs.length));
    }

    public static RobotInfo[] getRobotInfo(Robot[] robots, RobotController rc) throws GameActionException {
        RobotInfo[] infos = new RobotInfo[robots.length];
        for (int i = 0; i < robots.length; i++) {
            infos[i] = rc.senseRobotInfo(robots[i]);
        }
        return infos;
    }



    /**
     * Shoots cows around a pastr we can't attack
     * @param loc
     * @param robot
     * @return
     */
    public static MapLocation canSeePastrButNotShootItKillCowsInstead(MapLocation loc, TeamRobot robot) throws GameActionException {

        MapLocation toShoot = null;
        double maxCows = 0;
        Direction toRobot = loc.directionTo(robot.currentLoc);
        boolean range = false;
        MapLocation left, right;
        double cows, leftCows, rightCows;


        loc = loc.add(toRobot);
        if (robot.rc.canAttackSquare(loc)) {
            range = true;
            right = loc.add(toRobot.rotateRight().rotateRight());
            left = loc.add(toRobot.rotateLeft().rotateLeft());
            cows = robot.rc.senseCowsAtLocation(loc);
            leftCows = robot.rc.senseCowsAtLocation(left);
            rightCows = robot.rc.senseCowsAtLocation(right);
            if (cows > maxCows) {
                maxCows = cows;
                toShoot = loc;
            }
            if (leftCows > maxCows) {
                maxCows = leftCows;
                toShoot = right;
            }
            if (rightCows > maxCows) {
                maxCows = rightCows;
                toShoot = left;
            }
        }

        loc = loc.add(toRobot);
        if ((range || robot.rc.canAttackSquare(loc)) && !loc.equals(robot.currentLoc)) {
            right = loc.add(toRobot.rotateRight().rotateRight());
            left = loc.add(toRobot.rotateLeft().rotateLeft());
            cows = robot.rc.senseCowsAtLocation(loc);
            leftCows = robot.rc.senseCowsAtLocation(left);
            rightCows = robot.rc.senseCowsAtLocation(right);
            if (cows > maxCows) {
                maxCows = cows;
                toShoot = loc;
            }
            if (leftCows > maxCows) {
                maxCows = leftCows;
                toShoot = right;
            }
            if (rightCows > maxCows) {
                maxCows = rightCows;
                toShoot = left;
            }
        }


        return toShoot;
    }
}

package team009.combat;

import battlecode.common.*;


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

    public static MapLocation findCenterOfMass(MapLocation[] infos) {
        double x = 0.0;
        double y = 0.0;
        for (MapLocation i : infos) {
            x += i.x;
            y += i.y;
        }
        return new MapLocation((int)(x/infos.length), (int)(y/infos.length));
    }

    public static RobotInfo[] getRobotInfo(Robot[] robots, RobotController rc) throws GameActionException {
        RobotInfo[] infos = new RobotInfo[robots.length];
        for (int i = 0; i < robots.length; i++) {
            infos[i] = rc.senseRobotInfo(robots[i]);
        }
        return infos;
    }
}

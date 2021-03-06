package team009.navigation;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.robot.TeamRobot;

public abstract class Move {
    public MapLocation destination;
    protected TeamRobot robot;
    public int stepsTaken = 0;

    public Move(TeamRobot robot) {
        this.robot = robot;
    }

    public abstract boolean move() throws GameActionException;
    public abstract boolean sneak() throws GameActionException;

    public void setDestination(MapLocation destination) {
        if (destination == null) {
            this.destination = null;
            return;
        }

        if (destination.x >= robot.info.width) {
			destination = new MapLocation(robot.info.width - 1, destination.y);
		} else if (destination.x < 0) {
			destination = new MapLocation(0, destination.y);
		}
		if (destination.y >= robot.info.height) {
			destination = new MapLocation(destination.x, robot.info.height- 1);
		} else if (destination.y < 0) {
			destination = new MapLocation(destination.x, 0);
		}
        stepsTaken = 0;
        this.destination = destination;
    }

    public boolean atDestination() {
        return destination == null || robot.currentLoc.equals(destination);
    }
}

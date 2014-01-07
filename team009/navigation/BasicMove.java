package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Team;
import battlecode.common.TerrainTile;
import team009.robot.TeamRobot;

public class BasicMove {

	protected TeamRobot robot;
	public MapLocation destination;
	private Direction bug;
	private Direction lastBug;
	private Direction bugGoal;

	public BasicMove(TeamRobot robot) {
		this.robot = robot;
	}

	public void setDestination(MapLocation destination) {
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
		this.destination = destination;
	}

	public boolean atDestination() {
		return robot.currentLoc.equals(destination);
	}

	/**
	 * Dumb Move will walk over mines, and probably die. For use in extreme circumstances only!
	 * @throws GameActionException
	 */
	public void dumbMove() throws GameActionException {
		if (!robot.rc.isActive())
			return;

		Direction toMove = robot.currentLoc.directionTo(destination);

		if (toMove == Direction.NONE || toMove == Direction.OMNI)
			return;

		// if there is a move toward our goal without mines, take it
		if (robot.rc.canMove(toMove)) {
			robot.rc.move(toMove);
		} else if (robot.rc.canMove(toMove.rotateLeft())) {
			robot.rc.move(toMove.rotateLeft());
		} else if (robot.rc.canMove(toMove.rotateRight())) {
			robot.rc.move(toMove.rotateRight());
		}
		// otherwise move though the minefield!
		else if (robot.rc.canMove(toMove)) {
			robot.rc.move(toMove);
		} else if (robot.rc.canMove(toMove.rotateLeft())) {
			robot.rc.move(toMove.rotateLeft());
		} else if (robot.rc.canMove(toMove.rotateRight())) {
			robot.rc.move(toMove.rotateRight());
		}
	}

	public void move() throws GameActionException {
		if (!robot.rc.isActive())
			return;

		Direction toMove = robot.currentLoc.directionTo(destination);

		if (toMove == Direction.NONE || toMove == Direction.OMNI)
			return;

		if (bug != null) {

			if (robot.rc.canMove(bugGoal)) {
                robot.rc.move(bugGoal);
                lastBug = bug;
                bug = null;
                bugGoal = null;

			} else {
				robot.rc.setIndicatorString(0, "In bug moving " + bug.toString());
				moveWithDiffuse(bug);
			}
		}

		if (bug == null && robot.rc.isActive()) {
			moveWithDiffuse(toMove);
		}


	}

	private void moveWithDiffuse(Direction toMove) throws GameActionException {
		MapLocation ahead, left, right;
		ahead = robot.currentLoc.add(toMove);
		left = robot.currentLoc.add(toMove.rotateLeft());
		right = robot.currentLoc.add(toMove.rotateRight());

		// if there are no mines just try moving somewhere
		if (true) {
			if (robot.rc.canMove(toMove)) {
				robot.rc.move(toMove);
			} else if (robot.rc.canMove(toMove.rotateLeft())) {
				robot.rc.move(toMove.rotateLeft());
			} else if (robot.rc.canMove(toMove.rotateRight())) {
				robot.rc.move(toMove.rotateRight());
			}
		}

		// still can't move?  Activate/change bug direction
		if (robot.rc.senseTerrainTile(robot.currentLoc.add(toMove)) == TerrainTile.OFF_MAP) {
			bug = null;
		} else if (bugGoal != null && robot.rc.senseTerrainTile(robot.currentLoc.add(bugGoal)) == TerrainTile.OFF_MAP) {
			bug = null;
		} else if (robot.rc.isActive()) {
			// we aren't in bug and can't move, activate bug
			if (bug == null) {
				bugGoal = toMove;
				if (Math.random() < 0.5) {
					bug = toMove.rotateLeft().rotateLeft();
				} else {
					bug = toMove.rotateRight().rotateRight();
				}
				if (bug == lastBug) {
					bug = bug.opposite();
				}
			}
			// we are in bug and can't move our target direction
			// change our tracking direction!
			else {
				bug = bug.opposite();
			}
		}

	}

	public static MapLocation BoundToBoard(TeamRobot robot, MapLocation loc) {
        if (robot.rc.senseTerrainTile(loc) == TerrainTile.OFF_MAP) {
                int newX = loc.x, newY = loc.y;

                if (newX < 0) {
                        newX = 0;
                } else if (newX >= robot.info.width) {
                        newX = robot.info.width - 1;
                }

                if (newY < 0) {
                        newY = 0;
                } else if (newY >= robot.info.height) {
                        newY = robot.info.height - 1;
                }

                return new MapLocation(newX, newY);
        }
        return loc;
	}

}

package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.robot.TeamRobot;

@Deprecated
public class BasicMove extends Move {

	private Direction bug;
	private Direction lastBug;
	private Direction bugGoal;

	public BasicMove(TeamRobot robot) {
        super(robot);
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

    @Override
    public boolean move() throws GameActionException {
        move(false);
        return true;
    }

    @Override
    public boolean sneak() throws GameActionException {
        move(true);
        return true;
    }


	private void move(boolean sneak) throws GameActionException {
		if (!robot.rc.isActive())
			return;

		Direction toMove = robot.currentLoc.directionTo(destination);
        Direction result = null;

		if (toMove == Direction.NONE || toMove == Direction.OMNI)
			return;

		if (bug != null) {
			if (robot.rc.canMove(bugGoal)) {
                result = bugGoal;
                lastBug = bug;
                bug = null;
                bugGoal = null;

			} else {
				robot.rc.setIndicatorString(0, "In bug moving " + bug.toString());
				result = moveBug(bug);
			}
		}

		if (bug == null && robot.rc.isActive()) {
			result = moveBug(toMove);
		}

        if (result != null) {
            if (sneak) {
                robot.rc.sneak(result);
            } else {
                robot.rc.move(result);
            }
        }

	}

	private Direction moveBug(Direction toMove) throws GameActionException {
        Direction result = null;

		// if there are no mines just try moving somewhere
        if (robot.rc.canMove(toMove)) {
            result = toMove;
        } else if (robot.rc.canMove(toMove.rotateLeft())) {
            result = toMove.rotateLeft();
        } else if (robot.rc.canMove(toMove.rotateRight())) {
            result = toMove.rotateRight();
        }

		// still can't move?  Activate/change bug direction
		else if (robot.rc.senseTerrainTile(robot.currentLoc.add(toMove)) == TerrainTile.OFF_MAP) {
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
        return result;

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

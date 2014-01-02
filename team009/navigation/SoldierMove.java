package team009.navigation;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Team;
import battlecode.common.TerrainTile;
import team009.robot.TeamRobot;

public class SoldierMove {

	protected TeamRobot robot;
	public MapLocation destination;
	private Direction bug;
	private Direction lastBug;
	private Direction bugGoal;
	
	public SoldierMove(TeamRobot robot) {
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
        // TODO: get rid of currentLoc
		if (robot.rc.getLocation().equals(destination)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Dumb Move will walk over mines, and probably die. For use in extreme circumstances only!
	 * @throws GameActionException
	 */
	public void dumbMove() throws GameActionException {
		if (!robot.rc.isActive())
			return;

        // TODO: get rid of currentLoc
        MapLocation currentLoc = robot.rc.getLocation();
		Direction toMove = currentLoc.directionTo(destination);
		
		if (toMove == Direction.NONE || toMove == Direction.OMNI)
			return;
		
		// if there is a move toward our goal without mines, take it
		if (robot.rc.canMove(toMove) && robot.rc.senseMine(currentLoc.add(toMove)) == null) {
			robot.rc.move(toMove);
		} else if (robot.rc.canMove(toMove.rotateLeft()) && robot.rc.senseMine(currentLoc.add(toMove.rotateLeft())) == null) {
			robot.rc.move(toMove.rotateLeft());
		} else if (robot.rc.canMove(toMove.rotateRight()) && robot.rc.senseMine(currentLoc.add(toMove.rotateRight())) == null) {
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

        // TODO: get rid of currentLoc
        MapLocation currentLoc = robot.rc.getLocation();
		Direction toMove = currentLoc.directionTo(destination);
		
		if (toMove == Direction.NONE || toMove == Direction.OMNI)
			return;
		
		if (bug != null) {
			
			if (robot.rc.canMove(bugGoal)) {
				Team mine = robot.rc.senseMine(currentLoc.add(bugGoal));
				if (mine == Team.NEUTRAL || mine == robot.info.enemyTeam) {
					robot.rc.defuseMine(currentLoc.add(bugGoal));
				} else {
					robot.rc.move(bugGoal);
					lastBug = bug;
					bug = null;
					bugGoal = null;
				}
				
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
        // TODO: get rid of currentLoc
        MapLocation currentLoc = robot.rc.getLocation();
		ahead = currentLoc.add(toMove);
		left = currentLoc.add(toMove.rotateLeft());
		right = currentLoc.add(toMove.rotateRight());

		
		Team mineAheadTeam, mineLeftTeam, mineRightTeam, mineHereTeam;
		mineAheadTeam = robot.rc.senseMine(ahead);
		mineLeftTeam = robot.rc.senseMine(left);
		mineRightTeam = robot.rc.senseMine(right);
		mineHereTeam = robot.rc.senseMine(currentLoc);
		
		// if we have stepped on a mine
		if (mineHereTeam == robot.info.enemyTeam || mineHereTeam == Team.NEUTRAL) {
			// run away!
			// get off the mine square!
			toMove = currentLoc.directionTo(robot.info.hq);
			boolean done = false;
			int i = 0;
			while(!done && i < 8) {
				i++;
				if(robot.rc.canMove(toMove)) {
					Team mineTeam = robot.rc.senseMine(currentLoc.add(toMove));
					if (mineTeam != Team.NEUTRAL && mineTeam != robot.info.enemyTeam) {
						robot.rc.move(toMove);
						done = true;
					}
				}
				toMove = toMove.rotateLeft();
			}
		}
		
		// if we are next to our location but there is a mine on it
		else if (currentLoc.isAdjacentTo(destination) && (robot.rc.senseMine(currentLoc.add(toMove)) == Team.NEUTRAL ||
				robot.rc.senseMine(currentLoc.add(toMove)) == robot.info.enemyTeam)) {
			robot.rc.defuseMine(destination);
		}
		
		// if there are no mines just try moving somewhere
		else if (mineAheadTeam == null && mineLeftTeam == null && mineRightTeam == null) {
			if (robot.rc.canMove(toMove)) {
				robot.rc.move(toMove);
			} else if (robot.rc.canMove(toMove.rotateLeft())) {
				robot.rc.move(toMove.rotateLeft());
			} else if (robot.rc.canMove(toMove.rotateRight())) {
				robot.rc.move(toMove.rotateRight());
			}
		}
			
		// defuse enemy mines if we see them
		else if (mineAheadTeam == robot.info.enemyTeam) {
			robot.rc.defuseMine(ahead);
		} else if (mineLeftTeam == robot.info.enemyTeam) {
			robot.rc.defuseMine(left);
		} else if (mineRightTeam == robot.info.enemyTeam) {
			robot.rc.defuseMine(right);
		}
		
		// try to move to a spot with no neutral mines
		else if (mineAheadTeam != Team.NEUTRAL && robot.rc.canMove(toMove)) {
			robot.rc.move(toMove);
		}
		else if (mineLeftTeam != Team.NEUTRAL && robot.rc.canMove(toMove.rotateLeft())) {
			robot.rc.move(toMove.rotateLeft());
		}
		else if (mineRightTeam != Team.NEUTRAL && robot.rc.canMove(toMove.rotateRight())) {
			robot.rc.move(toMove.rotateRight());
		}
		
		// can't move, just try defusing
		else if (mineAheadTeam == Team.NEUTRAL) {
			robot.rc.defuseMine(ahead);
		}
		else if (mineRightTeam == Team.NEUTRAL) {
			robot.rc.defuseMine(right);
		}
		else if (mineLeftTeam == Team.NEUTRAL) {
			robot.rc.defuseMine(left);
		}
		
		// still can't move?  Activate/change bug direction
		if (robot.rc.senseTerrainTile(currentLoc.add(toMove)) == TerrainTile.OFF_MAP) {
			bug = null;
		} else if (bugGoal != null && robot.rc.senseTerrainTile(currentLoc.add(bugGoal)) == TerrainTile.OFF_MAP) {
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

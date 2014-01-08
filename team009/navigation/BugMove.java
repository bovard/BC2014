package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.robot.TeamRobot;

public class BugMove extends Move {
    private boolean bug = false;
    private boolean trackRight = Math.random() > .5;
    private MapLocation bugStart;
    private Direction bugStartDirection;
    private MapLocation lastPos;

    public BugMove(TeamRobot robot) {
        super(robot);
    }

    private void reset() {
        bug = false;
        lastPos = robot.currentLoc.add(Direction.EAST);
    }

    @Override
    public boolean move() throws GameActionException {
        return move(false);
    }

    @Override
    public boolean sneak() throws GameActionException {
        return move(true);
    }


    private boolean move(boolean sneak) throws GameActionException {
        if (!robot.rc.isActive())
            return false;

        Direction toMove = robot.currentLoc.directionTo(destination);
        if (toMove == Direction.NONE || toMove == Direction.OMNI)
            return false;

        // reset if needed
        if (!robot.currentLoc.isAdjacentTo(lastPos)) {
            reset();
        }


        Direction result = null;

        if (!bug) {
            result = simpleMove(toMove);
            if (result == null) {
                bug = true;
                setBugDirection();
                bugStartDirection = toMove;
                bugStart = robot.currentLoc;
            }
        }
        if (bug) {
            result = bugMove();
        }

        lastPos = robot.currentLoc;
        if (result != null) {
            if (sneak) {
                robot.rc.sneak(result);
            } else {
                robot.rc.move(result);
            }
            return true;
        }
        return false;
    }

    /**
     * Looks ahead/left/right and chooses a road if present
     * @param toMove direction we want to move
     * @return direction we should move (or null if we can't)
     */
    private Direction simpleMove(Direction toMove) {
        Direction left = toMove.rotateLeft();
        Direction right = toMove.rotateRight();

        TerrainTile[] canMove = new TerrainTile[3];
        Direction[] moveDir = new Direction[3];
        int items = 0;

        if (robot.rc.canMove(toMove)) {
            canMove[items] = robot.rc.senseTerrainTile(robot.currentLoc.add(toMove));
            moveDir[items] = toMove;
            items++;
        }

        // randomly add left then right or right then left
        if (Math.random() > .5) {
            if (robot.rc.canMove(left)) {
                canMove[items] = robot.rc.senseTerrainTile(robot.currentLoc.add(left));
                moveDir[items] = left;
                items++;
            }
            if (robot.rc.canMove(right)) {
                canMove[items] = robot.rc.senseTerrainTile(robot.currentLoc.add(right));
                moveDir[items] = right;
                items++;
            }
        } else {
            if (robot.rc.canMove(right)) {
                canMove[items] = robot.rc.senseTerrainTile(robot.currentLoc.add(right));
                moveDir[items] = right;
                items++;
            }
            if (robot.rc.canMove(left)) {
                canMove[items] = robot.rc.senseTerrainTile(robot.currentLoc.add(left));
                moveDir[items] = left;
                items++;
            }
        }

        // if we can't move anywhere, return null
        if (items == 0) {
            return null;
        }

        // if we can, move on a road
        for (int i = 0; i < items; i++) {
            if (canMove[i] == TerrainTile.ROAD) {
                return moveDir[i];
            }
        }

        // move straight if possible (otherwise it will be randomly left/right
        return moveDir[0];
    }

    private void setBugDirection() {
        trackRight = !trackRight;
    }


    private Direction bugMove() {
        Direction toMove = lastPos.directionTo(robot.currentLoc);

        if (trackRight) {
            toMove = toMove.rotateLeft().rotateLeft();
        } else {
            toMove = toMove.rotateRight().rotateRight();
        }

        int count = 0;
        while(!robot.rc.canMove(toMove) && count < 9) {
            if (trackRight) {
                toMove = toMove.rotateRight();
            } else {
                toMove = toMove.rotateLeft();
            }
            count++;
        }

        if (count == 9) {
            return null;
        }

        Direction breakDir = breakBug(bugStart, bugStartDirection);

        if (breakDir != null) {
            return breakDir;
        }

        return toMove;
    }

    private Direction breakBug(MapLocation start, Direction startDir) {
        Direction currDir = start.directionTo(robot.currentLoc);
        Direction left = currDir.rotateLeft();
        Direction right = currDir.rotateRight();
        if (currDir == startDir || currDir == left || currDir == right) {
            if (robot.rc.canMove(startDir)) {
                return startDir;
            }
            if (robot.rc.canMove(left)) {
                return left;
            }
            if (robot.rc.canMove(right)) {
                return right;
            }
        }
        return null;
    }
}

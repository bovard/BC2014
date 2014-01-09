package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.robot.TeamRobot;

public class BugMove extends Move {
    private static final int MAX_BUG_ROUNDS = 100;
    private boolean bug;
    private boolean trackRight = Math.random() > .5;
    private int startRound;
    private MapLocation bugStart;
    private Direction bugStartDirection;

    public BugMove(TeamRobot robot) {
        super(robot);
        bug = false;
    }

    private void reset() {
        System.out.println("Resetting!");
        trackRight = !trackRight;
        if (Math.random() < .1) {
            trackRight = Math.random() < .5;
        }
        bug = false;
        bugStart = null;
        bugStartDirection = null;
        startRound = 0;
    }

    @Override
    public boolean move() throws GameActionException {
        return moveWrapper(false);
    }

    @Override
    public boolean sneak() throws GameActionException {
        return moveWrapper(true);
    }


    private boolean moveWrapper(boolean sneak) throws GameActionException {
        Direction toMove = calcMove();

        if (toMove != null) {
            if (sneak) {
                robot.rc.sneak(toMove);
            } else {
                robot.rc.move(toMove);
            }
            return true;
        }
        return false;
    }


    private Direction calcMove() {
        if (!robot.rc.isActive())
            return null;

        Direction toMove = robot.currentLoc.directionTo(destination);
        if (toMove == Direction.NONE || toMove == Direction.OMNI)
            return null;

        Direction result = null;

        if (!bug) {
            result = simpleMove(toMove);
            robot.rc.setIndicatorString(0, "Starting move");
            if (result == null) {
                robot.rc.setIndicatorString(0, "Starting bug");
                bug = true;
                bugStartDirection = toMove;
                bugStart = robot.currentLoc;
                startRound = robot.round;
                int count = 0;
                while(!robot.rc.canMove(toMove) && count < 9) {
                    count++;
                    if (trackRight) {
                        toMove = toMove.rotateRight();
                    } else {
                        toMove = toMove.rotateLeft();
                    }
                }
                if (count == 9) {
                    robot.rc.setIndicatorString(0, "Starting bug no moves");
                    return null;
                }
                robot.rc.setIndicatorString(0, "Starting bug " + toMove.toString());
                return toMove;
            }
        }
        if (bug) {
            robot.rc.setIndicatorString(0, "Running bug");
            result = bugMove();
        }

        return result;
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


    private Direction bugMove() {
        Direction toMove = robot.lastLoc.directionTo(robot.currentLoc);
        if (toMove == Direction.OMNI || toMove == Direction.NONE) {
            System.out.println("Returning null!");
            return null;
        }

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
            // we've made it out of bug!
            System.out.println("Done with bug! Resetting");
            reset();
            return breakDir;
        }

        if (robot.round > startRound + MAX_BUG_ROUNDS) {
            System.out.println("Rounds timed out, resetting!");
            reset();
            return null;
        }

        return toMove;
    }

    private Direction breakBug(MapLocation start, Direction startDir) {
        Direction currDir = start.directionTo(robot.currentLoc);
        Direction left = currDir.rotateLeft();
        Direction right = currDir.rotateRight();
        if (currDir != Direction.OMNI && currDir != Direction.NONE
                && (currDir == startDir || currDir == left || currDir == right)) {
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

package team009.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.TerrainTile;
import team009.MapUtils;
import team009.robot.TeamRobot;

public class BugMove extends Move {
    private static final int MAX_BUG_ROUNDS = 100;
    private boolean bug;
    private boolean trackRight = robot.rand.nextDouble() > .5;
    private int startRound;
    private MapLocation bugStart;
    private Direction bugStartDirection;
    private MapLocation lastLoc;

    public BugMove(TeamRobot robot) {
        super(robot);
        bug = false;
        lastLoc = robot.currentLoc;
    }

    private void reset() {
        lastLoc = robot.currentLoc;
        trackRight = !trackRight;
        if (robot.rand.nextDouble() < .1) {
            trackRight = robot.rand.nextDouble() < .5;
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

        if (!robot.currentLoc.isAdjacentTo(lastLoc) && !robot.currentLoc.equals(lastLoc)) {
            //System.out.println("RESETTING BECAUSE NOT AT SAME LOC");
            reset();
        } else {
            lastLoc = robot.currentLoc;
        }

        Direction result = null;

        if (!bug) {
            result = simpleMove(toMove);
            if (result == null) {
                //System.out.println("ENTERING BUG STATE");
                bug = true;
                bugStartDirection = toMove;
                bugStart = robot.currentLoc;
                startRound = robot.round;
                lastLoc = robot.currentLoc;
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
                    return null;
                }
                return toMove;
            }
        }
        if (bug) {
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
        if (robot.rand.nextDouble() > .5) {
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
            return null;
        }

        if (!toMove.isDiagonal()) {
            Direction right = toMove.rotateRight().rotateRight();
            Direction left = toMove.rotateLeft().rotateLeft();

            if(robot.rc.canMove(toMove) && (robot.rc.canMove(right) || robot.rc.canMove(left))) {
                if (!MapUtils.isOnMap(robot.currentLoc.add(right), robot.info.width, robot.info.height)
                        || !MapUtils.isOnMap(robot.currentLoc.add(left), robot.info.width, robot.info.height)) {
                    // if we're tracking along the side of the wall something is wrong!
                    //System.out.println("REVERSING DUE TO EDGE OF MAP");
                    trackRight = !trackRight;
                    return toMove.opposite();
                }
            }
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
            reset();
            return breakDir;
        }

        if (robot.round > startRound + MAX_BUG_ROUNDS) {
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
            if (robot. rc.canMove(startDir)) {
                //System.out.println("Time to break");
                return startDir;
            }
            if (robot.rc.canMove(left)) {
                //System.out.println("Time to break");
                return left;
            }
            if (robot.rc.canMove(right)) {
                //System.out.println("Time to break");
                return right;
            }
        }
        return null;
    }
}

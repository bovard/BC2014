package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.navigation.Move;
import team009.navigation.SnailMove;
import team009.robot.soldier.ToySoldier;

public class ToyMoveToLocation extends Behavior {
    protected BugMove bugMove;
    protected SnailMove snailMove;
    protected Move currentMove;
    protected boolean usingSnail = true;
    public MapLocation currentLocation = new MapLocation(0, 0);
    ToySoldier soldier;
    private int attackRadius = 0;
    private int expectedSteps = 0;

    public ToyMoveToLocation(ToySoldier robot) {
        super(robot);
        soldier = robot;
        bugMove = new BugMove(robot);
        snailMove = new SnailMove(robot);
        currentMove = snailMove;
        attackRadius = RobotType.SOLDIER.attackRadiusMaxSquared;
        robot.rc.setIndicatorString(1, "Snail: " + expectedSteps);
    }

    @Override
    public boolean pre() throws GameActionException {
        return !currentMove.atDestination();
    }

    @Override
    public boolean run() throws GameActionException {
        update();
        if (currentMove.stepsTaken > expectedSteps) {
            if (usingSnail) {
                bugMove.setDestination(soldier.comLocation);
                currentMove = bugMove;
            } else {
                snailMove.setDestination(soldier.comLocation);
                currentMove = snailMove;
            }
            usingSnail = !usingSnail;
        }

        if (soldier.friendlyPastrs.length > 0 &&
            soldier.friendlyPastrs.arr[0].location.distanceSquaredTo(soldier.currentLoc) < attackRadius) {
            currentMove.sneak();
        } else {
            currentMove.move();
        }
        return true;
    }

    protected void update() throws GameActionException {
        if (!currentLocation.equals(soldier.comLocation)) {
            snailMove.setDestination(soldier.comLocation);
            bugMove.setDestination(soldier.comLocation);
            currentLocation = soldier.comLocation;
            expectedSteps = 4 * (int)Math.sqrt(currentLocation.distanceSquaredTo(robot.currentLoc));
        }
    }
}

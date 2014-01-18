package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class ToyMoveToLocation extends Behavior {
    protected BugMove move;
    public MapLocation currentLocation = new MapLocation(0, 0);
    ToySoldier soldier;

    public ToyMoveToLocation(ToySoldier robot) {
        super(robot);
        soldier = robot;
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return !move.atDestination();
    }

    @Override
    public boolean run() throws GameActionException {
        if (!currentLocation.equals(soldier.comLocation)) {
            move.setDestination(soldier.comLocation);
            currentLocation = soldier.comLocation;
        }
        //TODO determine when to sneak based on if near friendly PASTR
        move.move();
        //move.sneak();
        return true;
    }
}

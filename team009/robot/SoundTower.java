package team009.robot;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.SoundTowerBehavior;

public class SoundTower extends TeamRobot {
    private Direction[] directions;
    private int currentDirection;


    public SoundTower(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();

        // TODO: Make this settable by constructor (so we can communicate sectors to harvest from)
        directions = new Direction[8];
        directions[0] = Direction.NORTH;
        for (int i = 1; i < directions.length; i++) {
            directions[i] = directions[i - 1].rotateRight();
        }

        currentDirection = 0;
    }

    @Override
    protected Node getTreeRoot() {
        return new SoundTowerBehavior(this);
    }
}


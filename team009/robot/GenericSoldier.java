package team009.robot;

import battlecode.common.GameActionException;
import battlecode.common.GameObject;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.builder.TreeBuilder;

import java.awt.*;

public class GenericSoldier extends TeamRobot {
    public boolean seesEnemy;
    public GameObject[] enemies;

    public GenericSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        enemies = rc.senseNearbyGameObjects(Robot.class);
        seesEnemy = enemies.length > 0;
    }

    @Override
    protected Node getTreeRoot() {
        return TreeBuilder.getSoldierTree(this);
    }
}

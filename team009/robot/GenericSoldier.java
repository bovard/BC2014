package team009.robot;

import battlecode.common.GameActionException;
import battlecode.common.GameObject;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.SoldierSelector;

public class GenericSoldier extends TeamRobot {
    public boolean seesEnemy;
    public GameObject[] enemies;

    public GenericSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = enemies.length > 0;
    }

    @Override
    protected Node getTreeRoot() {
        return new SoldierSelector(this);
    }
}

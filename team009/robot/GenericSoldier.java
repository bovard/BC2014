package team009.robot;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.SoldierSelector;
import team009.communication.Communicator;

public class GenericSoldier extends TeamRobot {
    public boolean seesEnemy;
    public Robot[] enemies;
    public int group;
    public int type;

    public GenericSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = enemies.length > 0;

        if (Communicator.WriteRound()) {
            Communicator.WriteTypeAndGroup(rc, type, group);
        }
    }

    @Override
    protected Node getTreeRoot() {
        return new SoldierSelector(this);
    }
}

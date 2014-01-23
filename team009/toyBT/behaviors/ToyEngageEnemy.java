package team009.toyBT.behaviors;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.ToyCombat;
import team009.utils.SmartRobotInfoArray;

public class ToyEngageEnemy extends Behavior {
    ToySoldier soldier;
    ToyCombat combat;
    public ToyEngageEnemy(ToySoldier robot) {
        super(robot);
        soldier = robot;
        combat = new ToyCombat(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.seesEnemyTeamNonHQRobot;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better Micro
        combat.attack();
        return true;
    }
}

package team009.toyBT.micro;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class SoloEngageEnemies extends Behavior {
    public SoloEngageEnemies(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemySoldier && ((ToySoldier)robot).allies.length == 0;
    }

    @Override
    public boolean run() throws GameActionException {
        return false;
    }
}

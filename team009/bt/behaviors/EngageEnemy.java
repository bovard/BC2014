package team009.bt.behaviors;

import battlecode.common.GameActionException;
import team009.robot.GenericSoldier;
import team009.robot.TeamRobot;

public class EngageEnemy extends Behavior {
    public EngageEnemy(GenericSoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((GenericSoldier)robot).seesEnemy;
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
        rc.setIndicatorString(0, "I am engaging!");
        return false;
    }
}

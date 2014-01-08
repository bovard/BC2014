package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import team009.robot.GenericSoldier;

public class RetreatToPasture extends Behavior {
    GenericSoldier gs;
    boolean informedTeam = false;
    int roundInformedTeam = -1;

    public RetreatToPasture(GenericSoldier robot) {
        super(robot);
        gs = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return gs.seesEnemy;
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
        if (!informedTeam) {
            roundInformedTeam = robot.round;
            informedTeam = true;


        }

        return true;
    }
}


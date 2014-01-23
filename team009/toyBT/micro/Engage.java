package team009.toyBT.micro;

import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class Engage extends Behavior {
    public Engage(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).engagedInCombat;
    }

    @Override
    public boolean run() throws GameActionException {
        for (RobotInfo r : ((ToySoldier)robot).enemySoldiers.arr) {
            if (robot.rc.canAttackSquare(r.location)) {
                robot.rc.attackSquare(r.location);
                return true;
            }
        }
        System.out.println("something weird happened in Engage");
        return true;
    }
}

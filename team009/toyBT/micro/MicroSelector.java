package team009.toyBT.micro;

import battlecode.common.GameActionException;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class MicroSelector extends Selector {
    public MicroSelector(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemyTeamNonHQRobot;
    }
}

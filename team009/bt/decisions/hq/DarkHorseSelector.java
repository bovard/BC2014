package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.DarkHorsePlaceStructures;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class DarkHorseSelector extends Selector{

    public DarkHorseSelector(TeamRobot robot) {
        super(robot);
        children.add(new DarkHorsePlaceStructures(robot));
        children.add(new HQShoot(((HQ)robot)));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}

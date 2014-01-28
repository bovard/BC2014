package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.DarkHorsePlaceStructures;
import team009.hq.bt.behaviors.HQShoot;
import team009.bt.decisions.Selector;
import team009.robot.TeamRobot;
import team009.hq.HQ;

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

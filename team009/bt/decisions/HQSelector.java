package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.DumbPastrHunter;
import team009.bt.behaviors.hq.HQBalanced;
import team009.bt.behaviors.hq.HQDefensive;
import team009.bt.behaviors.hq.HQOffensive;
import team009.communication.TeamMemoryManager;
import team009.robot.HQ;


public class HQSelector extends Decision {
    public static int DEFENSIVE_PASTURE = 0;
    public static int PASTURE_HUNTERING = 1;
    public static int BALANCED = 2;
    public static int DUMB_PASTR_HUNT = 3;

    public int strat = BALANCED;
    protected TeamMemoryManager memoryManager;

    public HQSelector(HQ robot) {
        super(robot);
        memoryManager = new TeamMemoryManager(robot);
        strat = memoryManager.getHQStrategy();
        // TODO: Add in the Defensive Pasture and Pasture Hunting behaviors
        children.add(DEFENSIVE_PASTURE, new HQDefensive(robot));
        children.add(PASTURE_HUNTERING, new HQOffensive(robot));
        children.add(BALANCED, new HQBalanced(robot));
        children.add(DUMB_PASTR_HUNT, new DumbPastrHunter(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        // always should enter here
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        memoryManager.writeMemeory();
        // all of these should have no pres, or posts so don't have to check
        if (strat == BALANCED) {
            return children.get(BALANCED).run();
        } else if (strat == DEFENSIVE_PASTURE) {
            return children.get(DEFENSIVE_PASTURE).run();
        } else if (strat == PASTURE_HUNTERING) {
            return children.get(PASTURE_HUNTERING).run();
        } else if (strat == DUMB_PASTR_HUNT) {
            return children.get(DUMB_PASTR_HUNT).run();
        } else {
            System.out.println("No strat selected!");
            return false;
        }
    }
}

package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.bt.behaviors.hq.*;
import team009.communication.TeamMemoryManager;
import team009.robot.HQ;


public class HQSelector extends Decision {
    public static int DEFENSIVE_PASTURE = 0;
    public static int PASTURE_HUNTING = 1;
    public static int BALANCED = 2;
    public static int DUMB_PASTR_HUNT = 3;
    public Node canAttack;
    public Node soundTower;

    public int strat = BALANCED;
    protected TeamMemoryManager memoryManager;

    public HQSelector(HQ robot) {
        super(robot);
        memoryManager = new TeamMemoryManager(robot);
        strat = memoryManager.getHQStrategy();
        // TODO: Add in the Defensive Pasture and Pasture Hunting behaviors
        addChild(new HQDefensive(robot));
        addChild(new HQOffensive(robot));
        addChild(new HQBalanced(robot));
        addChild(new DumbPastrHunter(robot));
        canAttack = new HQShoot(robot);
        soundTower = new HQSoundTower(robot);
        strat = PASTURE_HUNTING;
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

        // TODO: After sprint this behavior will have to change.
        //first see if the HQ can attack, so manually inject the super.run() bits
        if (canAttack.pre()) {
            if (canAttack.post()) {
                canAttack.reset();
            }
            canAttack.run();
        }

        // all of these should have no pres, or posts so don't have to check
        if (soundTower.pre()) {
            return soundTower.run();
        } else if (strat == BALANCED) {
            return children.get(BALANCED).run();
        } else if (strat == DEFENSIVE_PASTURE) {
            return children.get(DEFENSIVE_PASTURE).run();
        } else if (strat == DUMB_PASTR_HUNT) {
            return children.get(DUMB_PASTR_HUNT).run();
        } else if (strat == PASTURE_HUNTING) {
            return children.get(PASTURE_HUNTING).run();
        } else {
            System.out.println("No strat selected!");
            return false;
        }
    }
}

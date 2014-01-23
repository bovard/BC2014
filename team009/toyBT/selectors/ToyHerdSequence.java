package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.decisions.Sequence;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyHerdReturn;
import team009.toyBT.behaviors.ToyHerdSneak;

public class ToyHerdSequence extends Sequence {
    ToyHerdReturn herdReturn;
    ToyHerdSneak sneak;

    public ToyHerdSequence(ToySoldier robot) {
        super(robot);
        // TODO: make more efficient sequence in the run
        children.add(new ToyHerdReturn(robot));
        children.add(new ToyHerdSneak(robot));
        lastRun = 0;
    }

    @Override
    public boolean pre() throws GameActionException {
        return false;
    }
}

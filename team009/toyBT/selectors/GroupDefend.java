package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupDefend extends ToyMoveToLocation {
    ToySoldier soldier;

    public GroupDefend(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.hasDefendPastrSignal();
    }

    public boolean run() throws GameActionException {

        // We know that the location has been set and that there is an attack signal.
        setDestination(soldier.getHQCommandLocation());

        return super.run();
    }
}


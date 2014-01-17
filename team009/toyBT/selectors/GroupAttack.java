package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyMoveToLocation;

public class GroupAttack extends ToyMoveToLocation {
    ToySoldier soldier;

    public GroupAttack(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.hasAttackSignal();
    }

    public boolean run() throws GameActionException {

        // We know that the location has been set and that there is an attack signal.
        setDestination(soldier.groupCommand.location);

        return super.run();
    }
}

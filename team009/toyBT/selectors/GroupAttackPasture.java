package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;

public class GroupAttackPasture extends PushToLocation {
    ToySoldier soldier;

    public GroupAttackPasture(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.hasPastrAttackSignal();
    }

    public boolean run() throws GameActionException {

        // We know that the location has been set and that there is an attack signal.
        location.setDestination(soldier.groupCommand.location);

        return super.run();
    }
}

package team009.toyBT.micro;

import battlecode.common.GameActionException;
import team009.bt.decisions.Selector;
import team009.robot.soldier.ToySoldier;

public class CombatSelector extends Selector {
    public CombatSelector(ToySoldier robot) {
        super(robot);
        // okay so we are engaged in combat
        // if we are outnumbered check to see if we can suicide
        // then check to see if we can retreat safely
        // else shoot someone

        children.add(new Retreat(robot));
        children.add(new Suicide(robot));
        children.add(new ShootSomeone(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).engagedInCombat;
    }
}

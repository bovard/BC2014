package team009.toyBT;

import battlecode.common.GameActionException;
import team009.bt.decisions.Decision;
import team009.bt.decisions.Selector;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.selectors.*;

public class ToySelector extends Decision {
    GroupDestruct destruct;
    GroupAttack attack;
    GroupDefend defend;
    GroupAttackPasture attackPasture;
    GroupCapture capturePasture;
    GroupReturnToBase returnToBase;
    public ToySelector(ToySoldier robot) {
        super(robot);

        // Attack as a group
        destruct = new GroupDestruct(robot);

        // Attack as a group
        attack = new GroupAttack(robot);

        // Defends pasture as group
        defend = new GroupDefend(robot);

        // Attacks pasture as group
        capturePasture = new GroupCapture(robot);

        // Attacks pasture as group
        attackPasture = new GroupAttackPasture(robot);

        // returns to base as group
        returnToBase = new GroupReturnToBase(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {

        // NOTE:  This is obviously brittle, but its really efficient.
        // Byte code critical code
        if (destruct.pre()) {
            destruct.run();
        } else if (attack.pre()) {
            attack.run();
        } else if (defend.pre()) {
            defend.run();
        } else if (capturePasture.pre()) {
            capturePasture.run();
        } else if (attackPasture.pre()) {
            attackPasture.run();
        } else if (returnToBase.pre()) {
            returnToBase.run();
        }
        return true;
    }
}


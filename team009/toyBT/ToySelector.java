package team009.toyBT;

import battlecode.common.GameActionException;
import team009.bt.decisions.Decision;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.*;
import team009.toyBT.selectors.ToyHerderSelector;

public class ToySelector extends Decision {
    GroupDestruct destruct;
    GroupAttack attack;
    GroupDefend defend;
    GroupAttackPasture attackPasture;
    GroupCapture capturePasture;
    GroupReturnToBase returnToBase;
    ToyEngageEnemy engageEnemy;
    ToyHerderSelector herder;
    ToySoldier soldier;
    public ToySelector(ToySoldier robot) {
        super(robot);
        soldier = robot;

        // Kill dem enemies
        engageEnemy = new ToyEngageEnemy(robot);

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

        // Becomes the herder.
        herder = new ToyHerderSelector(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {

        // NOTE:  This is obviously brittle, but its really efficient.
        // Byte code critical code
        rc.setIndicatorString(0, "Command: " + soldier.comCommand + " : Location: " + soldier.comLocation);
        if (engageEnemy.pre()) {
            return engageEnemy.run();
        }
        if (destruct.pre()) {
            destruct.run();
        } else if (attack.pre()) {
            attack.run();
        }

        if (soldier.isHerder) {
            herder.run();
        } else if (soldier.isHunter) {
            if (defend.pre()) {
                defend.run();
            } else if (capturePasture.pre()) {
                capturePasture.run();
            } else if (attackPasture.pre()) {
                attackPasture.run();
            } else if (returnToBase.pre()) {
                returnToBase.run();
            }

        }
        return true;
    }
}


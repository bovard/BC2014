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
    GroupReturnToBase returnToBase;
    ToyEngageEnemy engageEnemy;
    ToyHerderSelector herder;
    NearEnemyHQ nearHq;
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
        attackPasture = new GroupAttackPasture(robot);

        // returns to base as group
        returnToBase = new GroupReturnToBase(robot);

        // Becomes the herder.
        herder = new ToyHerderSelector(robot);

        nearHq = new NearEnemyHQ(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {

        // NOTE:  This is obviously brittle, but its really efficient.
        // Byte code critical code
        rc.setIndicatorString(0, "Command: " + soldier.comCommand + " : Location: " + soldier.comLocation + " : group: " + soldier.group);
        if (engageEnemy.pre()) {
            return engageEnemy.run();
        }
        if (destruct.pre()) {
            return destruct.run();
        } else if (nearHq.pre()) {
            return nearHq.run();
        } else if (attack.pre()) {
            return attack.run();
        }

        if (soldier.isHerder) {
            herder.run();
        } else if (soldier.isHunter) {
            if (defend.pre()) {
                defend.run();
            } else if (attackPasture.pre()) {
                attackPasture.run();
            } else if (returnToBase.pre()) {
                returnToBase.run();
            }
        }
        return true;
    }
}


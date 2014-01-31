package team009.toyBT;

import battlecode.common.GameActionException;
import team009.bt.decisions.Decision;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.*;
import team009.toyBT.micro.MicroSelector;
import team009.toyBT.selectors.ToyHerderSelector;

public class ToySelector extends Decision {
    GroupDestruct destruct;
    GroupAttack attack;
    GroupDefend defend;
    GroupAttackPasture attackPasture;
    GroupReturnToBase returnToBase;
    ToyEngageEnemy engageEnemy;
    CaptureDefend capture;
    NearEnemyHQ nearHq;
    ToySoldier soldier;
    MicroSelector micro;
    public ToySelector(ToySoldier robot) {
        super(robot);
        soldier = robot;

        // Kill dem enemies
        engageEnemy = new ToyEngageEnemy(robot);

        // Kill dem enemies v2
        micro = new MicroSelector(robot);

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

        // For capturing and defending sound/pastrs
        capture = new CaptureDefend(robot);

        nearHq = new NearEnemyHQ(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    public boolean run() throws GameActionException {

        // NOTE:  This is obviously brittle, but its really efficient.
        // Byte code critical code
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

        if (capture.pre()) {
            capture.run();
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


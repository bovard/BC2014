package team009.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.combat.DumbCombat;
import team009.robot.HQ;
import team009.robot.soldier.BaseSoldier;

public class HQEngageEnemy extends Behavior {
    private HQ hq;
    public HQEngageEnemy(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return hq.seesEnemy;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better Micro
        //DumbCombat.Attack(rc, hq.enemies, robot.currentLoc);
        for (int i = 0; i < hq.enemies.length; i++) {
            RobotInfo info = rc.senseRobotInfo(hq.enemies[i]);

            // TODO: Actual micro!
            if (hq.rc.canAttackSquare(info.location)) {
                hq.rc.attackSquare(info.location);
                break;
            }
        }
        return true;
    }
}

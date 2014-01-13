package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import team009.bt.behaviors.Behavior;
import team009.combat.CombatUtils;
import team009.robot.hq.HQ;

public class HQShoot extends Behavior {
    private HQ hq;
    public HQShoot(HQ robot) {
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
        RobotInfo[] infos = CombatUtils.getRobotInfo(hq.enemies, robot.rc);

        System.out.println("Shooting");
        System.out.println("Looking for direct hits");
        // looking for direct hits
        for (int i = 0; i < infos.length; i++) {
            RobotInfo info = infos[i];
            System.out.println(info.location.toString());

            // TODO: Actual micro!
            if (hq.rc.canAttackSquare(info.location)) {
                System.out.println("Shooting " + info.location.toString());
                hq.rc.attackSquare(info.location);
                return true;
            }
        }
        // second pass, look for splash hits
        System.out.println("Looking for splash hits");
        for (int i = 0; i < infos.length; i++) {
            RobotInfo info = infos[i];
            System.out.println(info.location.toString());
            MapLocation toHit = info.location.add(info.location.directionTo(hq.info.hq));
            if (hq.rc.canAttackSquare(toHit)) {
                System.out.println("Shooting " + toHit.toString());
                hq.rc.attackSquare(toHit);
                return true;
            }

        }
        System.out.println("Didn't shoot");

        return false;
    }
}

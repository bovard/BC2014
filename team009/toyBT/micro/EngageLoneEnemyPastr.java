package team009.toyBT.micro;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import team009.bt.behaviors.Behavior;
import team009.combat.CombatUtils;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class EngageLoneEnemyPastr extends Behavior {

    private BugMove move;

    public EngageLoneEnemyPastr(ToySoldier robot) {
        super(robot);
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemyPastr ;
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.setIndicatorString(2, "Engage Pastr " + Clock.getRoundNum());
        // assume that they are sorted in closest to furthest
        RobotInfo pastr = ((ToySoldier)robot).enemyPastrs.arr[0];

        // look for cows to shoot

        if (robot.rc.canAttackSquare(pastr.location)) {
            robot.rc.attackSquare(pastr.location);
            return true;
        }

        if (!((ToySoldier)robot).seesEnemyHQ)  {
            move.setDestination(pastr.location);
            if (move.move()) {
                return true;
            }
        }

        MapLocation toShoot = CombatUtils.canSeePastrButNotShootItKillCowsInstead(pastr.location, robot);
        if (toShoot != null && robot.rc.canAttackSquare(toShoot)) {
            robot.rc.attackSquare(toShoot);
            return true;
        }

        System.out.println("Something weird happened in pastr engage");
        return true;
    }
}

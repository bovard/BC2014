package team009.toyBT.micro;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;

public class EnageLoneEnemyNoise extends Behavior {

    private BugMove move;

    public EnageLoneEnemyNoise(ToySoldier robot) {
        super(robot);
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemyNoise && !((ToySoldier)robot).seesEnemyHQ;
    }

    @Override
    public boolean run() throws GameActionException {
        // assume senseNearbyGameObjects returns them in distance from us
        robot.rc.setIndicatorString(2, "Engage Pastr " + Clock.getRoundNum());
        MapLocation toAttack = ((ToySoldier)robot).enemyNoise.arr[0].location;

        if (robot.rc.canAttackSquare(toAttack)) {
            robot.rc.attackSquare(toAttack);
            return true;
        } else {
            move.setDestination(toAttack);
            move.move();
            return true;
        }
    }
}

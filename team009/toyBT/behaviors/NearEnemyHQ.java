package team009.toyBT.behaviors;

import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.RobotInformation;
import team009.bt.behaviors.Behavior;
import battlecode.common.GameActionException;
import team009.robot.soldier.ToySoldier;

public class NearEnemyHQ extends Behavior {
    ToySoldier soldier;
    RobotInformation info;
    MapLocation nme;

    public NearEnemyHQ(ToySoldier soldier) {
        super(soldier);
        this.info = soldier.info;
        nme = info.enemyHq;
    }

    @Override
    public boolean pre() throws GameActionException {
        return nme.distanceSquaredTo(robot.currentLoc) < HQ_PROXIMITY;
    }

    public boolean run() throws GameActionException {
        rc.selfDestruct();
        return true;
    }

    public static final int HQ_PROXIMITY = (int)Math.pow(Math.sqrt(RobotType.HQ.attackRadiusMaxSquared) + 4, 2);
}


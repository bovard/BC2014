package team009.toyBT.behaviors;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import team009.RobotInformation;
import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.ToySoldier;
import team009.utils.HQAttackUtil;

public class NearEnemyHQ extends Behavior {
    ToySoldier soldier;
    RobotInformation info;
    MapLocation nme;
    MapLocation hq;
    BugMove move;
    MapLocation[] corners = new MapLocation[4];
    HQAttackUtil hqAttack;

    public NearEnemyHQ(ToySoldier soldier) {
        super(soldier);
        this.info = soldier.info;
        nme = info.enemyHq;
        hq = info.hq;
        move = new BugMove(soldier);
        this.soldier = soldier;
        hqAttack = soldier.hqAttack;

        corners[0] = new MapLocation(0, 0);
        corners[1] = new MapLocation(0, soldier.info.height);
        corners[2] = new MapLocation(soldier.info.width, soldier.info.height);
        corners[3] = new MapLocation(soldier.info.width, 0);
    }

    @Override
    public boolean pre() throws GameActionException {
        return hqAttack.inProximity(soldier.currentLoc);
    }

    public boolean run() throws GameActionException {
        if (hqAttack.toClose(soldier.currentLoc)) {
            if (move.destination == null || !move.destination.equals(hq)) {
                move.setDestination(hq);
            }

            int i = 0;
            Direction dir = move.calcMove();

            for (; i < 5; i++) {
                if (dir != null) {
                    MapLocation newMap = robot.currentLoc.add(dir);
                    if (!hqAttack.toClose(newMap)) {
                        move.move();
                    }
                }
                if (i < 4) {
                    move.setDestination(corners[i]);
                }
            }
        }
        return true;
    }
}


package team009.bt.behaviors.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.hq.HQ;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.SoldierSpawner;

public class HQAction extends Behavior {
    HQ hq;
    MapLocation center = null;
    boolean debugCenter = false;

    public HQAction(HQ hq) {
        super(hq);
        this.hq = hq;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (center == null) {
            center = new MapLocation(robot.info.width / 2, robot.info.height / 2);
        }

        robot.message += robot.round + " Action ";
        if (debugCenter) {
            robot.message += " (Debug) ";
            if (Clock.getRoundNum() > 400) {
                hq.comDefend(center, BaseSoldier.DEFENDER_GROUP);
            }
        } else {
            MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
            int soldierCount = hq.getCount(SoldierSpawner.SOLDIER_TYPE_DEFENDER, BaseSoldier.DEFENDER_GROUP);

            if (locs.length > 0 && soldierCount > 3) {
                hq.comDefend(locs[0], BaseSoldier.DEFENDER_GROUP);
            }
        }

        return true;
    }
}

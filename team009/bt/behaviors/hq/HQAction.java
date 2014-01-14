package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.hq.HQ;
import team009.robot.soldier.BaseSoldier;

public class HQAction extends Behavior {
    HQ hq;

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
        MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
        if (locs.length > 0) {
            hq.comDefend(locs[0], BaseSoldier.DEFENDER_GROUP);
        }

        return true;
    }
}

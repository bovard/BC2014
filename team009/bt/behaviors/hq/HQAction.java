package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.communication.Communicator;
import team009.robot.hq.Offensive;
import team009.robot.soldier.BaseSoldier;

public class HQAction extends Behavior {
    Offensive offensive;

    public HQAction(Offensive offensive) {
        super(offensive);
        this.offensive = offensive;
    }

    @Override
    public boolean pre() throws GameActionException {
        return Communicator.WriteRound(offensive.round);
    }

    @Override
    public boolean run() throws GameActionException {
        MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
        if (locs.length > 0) {
            Communicator.WriteToGroup(rc, BaseSoldier.DEFENDER_GROUP, BaseSoldier.ATTACK);
        }

        return true;
    }
}

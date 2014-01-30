package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.communication.bt.behaviors.ReadBehavior;
import team009.communication.decoders.TwoWayDecoder;
import team009.hq.HQPreprocessor;
import team009.robot.TeamRobot;
import team009.hq.HQ;
import team009.utils.SmartMapLocationArray;

public class HQStateCom extends ReadBehavior {
    HQ hq;

    private MapLocation from;
    private MapLocation to;

    public HQStateCom(HQ hq) {
        super(hq);
        this.hq = hq;
        from = rc.senseHQLocation();
        to = rc.senseEnemyHQLocation();
    }

    public boolean run() throws GameActionException {
        // if we have an aStar and we are processing, process away!
        if (((HQPreprocessor)hq).a != null && to != null && from != null) {
            MapLocation done = ((HQPreprocessor)hq).a.getNextWayPoint(from, to);
            // the Map Location will be non null if it's done processing!
            if (done != null) {
                from = null;
                to = null;
            }
        }


        hq.soldierCounts = Communicator.ReadTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_TOY_SOLDIER, 0);
        hq.pastrCounts = Communicator.ReadTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_PASTR, TeamRobot.PASTR_GROUP);
        hq.noiseCounts = Communicator.ReadTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_NOISE_TOWER, TeamRobot.NOISE_TOWER_GROUP);

        hq.noiseLocations = new SmartMapLocationArray();
        for (int i = 0; i < Communicator.TWO_WAY_COM_LENGTH; i++) {
            TwoWayDecoder dec = Communicator.ReadTwoWayCommunicate(rc, i);
            if (dec.command == TeamRobot.POSITION_OF_NOISE_TOWER) {
                hq.noiseLocations.add(dec.from);
            } else if (dec.command == TeamRobot.POSITION_OF_PASTR) {
                hq.pastrLocations.add(dec.from);
            } else if (dec.command == TeamRobot.REQUEST_LOCATION) {
                if (((HQPreprocessor)hq).a != null) {
                    MapLocation from = dec.from;
                    MapLocation to = dec.to;
                    MapLocation result = ((HQPreprocessor)hq).a.getCachedWayPoint(from, to);
                    if (result == null) {
                        // Nothing, channel is cleared below
                    } else {
                        Communicator.WriteTwoWayCommunicate(rc, i + Communicator.TWO_WAY_HQ_COM_BASE, TeamRobot.LOCATION_RESULT, from, result);
                    }
                }
            }
            Communicator.ClearTwoWayChannel(rc, i);
        }

        return true;
    }
}

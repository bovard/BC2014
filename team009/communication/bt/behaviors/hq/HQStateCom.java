package team009.communication.bt.behaviors.hq;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.communication.bt.behaviors.ReadBehavior;
import team009.communication.decoders.TwoWayDecoder;
import team009.robot.TeamRobot;
import team009.hq.HQ;
import team009.utils.SmartMapLocationArray;

public class HQStateCom extends ReadBehavior {
    HQ hq;

    public HQStateCom(HQ hq) {
        super(hq);
        this.hq = hq;
    }

    public boolean run() throws GameActionException {
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
            }
            Communicator.ClearTwoWayChannel(rc, i);
        }

        return true;
    }
}

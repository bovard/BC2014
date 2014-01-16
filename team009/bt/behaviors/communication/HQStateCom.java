package team009.bt.behaviors.communication;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.communication.SoldierCountDecoder;
import team009.robot.hq.HQ;

public class HQStateCom extends ReadBehavior {
    HQ hq;
    SoldierCountDecoder[] soldierCounts;

    public HQStateCom(HQ hq) {
        super(hq);
        this.hq = hq;
        soldierCounts = hq.soldierCounts;
    }

    public boolean run() throws GameActionException {
        int groupCount = Communicator.MAX_GROUP_COUNT;

        for (int i = 0; i < hq.maxSoldiers; i++) {
            int group = i % groupCount;
            int type = i / groupCount;
            soldierCounts[i] = Communicator.ReadTypeAndGroup(rc, type, group);
            Communicator.ClearCountChannel(rc, type, group);
        }
        return true;
    }
}

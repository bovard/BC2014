package team009.communication.bt.behaviors;

import battlecode.common.GameActionException;
import team009.communication.Communicator;
import team009.communication.SoldierCountDecoder;
import team009.robot.hq.HQ;
import team009.robot.soldier.SoldierSpawner;

public class HQReadCom extends ReadBehavior {
    HQ hq;
    SoldierCountDecoder[] soldierCounts;

    public HQReadCom(HQ hq) {
        super(hq);
        this.hq = hq;
        soldierCounts = hq.soldierCounts;
    }

    public boolean run() throws GameActionException {
        int type = SoldierSpawner.SOLDIER_TYPE_TOY_SOLDIER;
        int groups = Communicator.MAX_GROUP_COUNT;

        for (int i = 0; i < groups; i++) {
            soldierCounts[i] = Communicator.ReadTypeAndGroup(rc, type, i);
            Communicator.ClearCountChannel(rc, type, i);
        }
        return true;
    }
}

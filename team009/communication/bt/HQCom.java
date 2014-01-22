package team009.communication.bt;

import team009.communication.bt.behaviors.hq.HQStateCom;
import team009.robot.hq.Seeding;

public class HQCom extends Com {
    public HQCom(Seeding robot) {
        super(robot);
        write = new HQOffensiveWriteCom(robot);
        read = new HQStateCom(robot);
    }
}

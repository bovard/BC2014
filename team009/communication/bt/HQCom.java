package team009.communication.bt;

import team009.communication.bt.behaviors.hq.HQClearStateCom;
import team009.communication.bt.behaviors.hq.HQStateCom;
import team009.robot.hq.Offensive;

public class HQCom extends Com {
    public HQCom(Offensive robot) {
        super(robot);
        write = new HQOffensiveWriteCom(robot);
        read = new HQStateCom(robot);
        extraCom = new HQClearStateCom(robot);
    }
}

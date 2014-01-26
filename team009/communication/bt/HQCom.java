package team009.communication.bt;

import team009.communication.bt.behaviors.hq.HQClearStateCom;
import team009.communication.bt.behaviors.hq.HQStateCom;
import team009.robot.hq.Qualifier;

public class HQCom extends Com {
    public HQCom(Qualifier robot) {
        super(robot);
        write = new HQOffensiveWriteCom(robot);
        read = new HQStateCom(robot);
        postCom = new HQClearStateCom(robot);
    }
}

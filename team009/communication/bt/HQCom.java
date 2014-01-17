package team009.communication.bt;

import team009.communication.bt.behaviors.HQReadCom;
import team009.communication.bt.behaviors.HQWriteCom;
import team009.robot.hq.HQ;

public class HQCom extends Com {
    public HQCom(HQ robot) {
        super(robot);
        write = new HQWriteCom(robot);
        read = new HQReadCom(robot);
    }
}

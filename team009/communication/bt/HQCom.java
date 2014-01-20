package team009.communication.bt;

import team009.communication.bt.behaviors.HQOffensiveCom;
import team009.communication.bt.behaviors.HQStateCom;
import team009.robot.hq.Offensive;

public class HQCom extends Com {
    public HQCom(Offensive robot) {
        super(robot);
        write = new HQOffensiveCom(robot);
        read = new HQStateCom(robot);
    }
}

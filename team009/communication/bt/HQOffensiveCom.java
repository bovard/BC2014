package team009.communication.bt;

import team009.communication.bt.behaviors.HQOffensiveWriteCom;
import team009.communication.bt.behaviors.HQStateCom;
import team009.robot.hq.Offensive;

public class HQOffensiveCom extends Com {
    public HQOffensiveCom(Offensive robot) {
        super(robot);
        write = new HQOffensiveWriteCom(robot);
        read = new HQStateCom(robot);
    }
}

package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.*;
import team009.bt.decisions.Decision;
import team009.hq.robot.Offensive;

// TODO: Make this more efficient by hard coding the available states.
public class OffensiveSelector extends Decision {
    HQShoot shoot;
    HQOffensive spawn;

    Offensive hq;

    public OffensiveSelector(Offensive robot) {
        super(robot);
        shoot = new HQShoot(robot);
        spawn = new HQOffensive(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (shoot.pre()) {
            if(shoot.run()) {
                // if we actually shoot return
                return true;
            }
        }

        spawn.run();

        return true;
    }
}

package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQOffensive;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.decisions.Decision;
import team009.robot.hq.Qualifier;

// TODO: Make this more efficient by hard coding the available states.
public class QualifierSelector extends Decision {
    HQShoot shoot;
    HQOffensive spawn;

    Qualifier hq;

    public QualifierSelector(Qualifier robot) {
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


package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.HQSpawn;
import team009.hq.bt.behaviors.HQQualifier;
import team009.hq.bt.behaviors.HQShoot;
import team009.bt.decisions.Decision;
import team009.hq.robot.Qualifier;

// TODO: Make this more efficient by hard coding the available states.
public class QualifierSelector extends Decision {
    HQShoot shoot;
    HQSpawn spawn;
    HQQualifier qualifierBehavior;

    Qualifier q;

    public QualifierSelector(Qualifier robot) {
        super(robot);
        shoot = new HQShoot(robot);
        spawn = new HQSpawn(robot);
        qualifierBehavior = new HQQualifier(robot);
        q = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        qualifierBehavior.run();
        if (shoot.pre()) {
            if (shoot.run()) {
                return true;
            }
        }
        spawn.run();
        return true;
    }
}


package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQOffensive;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.behaviors.hq.HQSoundTower;
import team009.bt.decisions.Decision;
import team009.bt.decisions.Selector;
import team009.robot.hq.HQ;
import team009.robot.hq.Offensive;

// TODO: Make this more efficient by hard coding the available states.
public class OffensiveSelector extends Decision {
    HQShoot shoot;
    HQOffensive spawn;
    HQSoundTower sound;

    Offensive hq;

    public OffensiveSelector(Offensive robot) {
        super(robot);
        shoot = new HQShoot(robot);
        spawn = new HQOffensive(robot);
        sound = new HQSoundTower(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (shoot.pre()) {
            shoot.run();
        } else if (hq.dark && sound.pre()) {
            sound.run();
        } else {
            spawn.run();
        }

        return true;
    }
}

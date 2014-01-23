package team009.bt.decisions.hq;

import battlecode.common.GameActionException;
import team009.bt.behaviors.hq.HQCheese;
import team009.bt.behaviors.hq.HQSeeding;
import team009.bt.behaviors.hq.HQShoot;
import team009.bt.decisions.Decision;
import team009.robot.hq.Seeding;

// TODO: Make this more efficient by hard coding the available states.
public class SeedingSelector extends Decision {
    HQShoot shoot;
    HQSeeding spawn;
    HQCheese sound;

    Seeding hq;

    public SeedingSelector(Seeding robot) {
        super(robot);
        shoot = new HQShoot(robot);
        spawn = new HQSeeding(robot);
        sound = new HQCheese(robot);
        hq = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        System.out.println("Running: " + hq.cheese);
        boolean shot = false;
        if (shoot.pre()) {
            shot = shoot.run();
        }

        if (shot) {
            return true;
        }

        if (hq.cheese && sound.pre()) {
            sound.run();
        } else {
            spawn.run();
        }

        return true;
    }
}

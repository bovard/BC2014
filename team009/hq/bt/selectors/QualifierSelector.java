package team009.hq.bt.selectors;

import battlecode.common.GameActionException;
import team009.hq.bt.behaviors.HQOffensive;
import team009.hq.bt.behaviors.HQShoot;
import team009.bt.decisions.Decision;
import team009.hq.robot.Qualifier;

// TODO: Make this more efficient by hard coding the available states.
public class QualifierSelector extends Decision {
    HQShoot shoot;
    HQOffensive spawn;

    Qualifier q;

    public QualifierSelector(Qualifier robot) {
        super(robot);
        shoot = new HQShoot(robot);
        spawn = new HQOffensive(robot);
        q = robot;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        boolean ran = false;
        if (shoot.pre()) {
            ran = shoot.run();
        }

        // Communications information
        if (q.soldierCounts.count > 8 && q.soldierCounts.centroid.distanceSquaredTo(q.info.enemyHq) < 100) {
            rc.setIndicatorString(2, "Surround Technique: " + q.soldierCounts.centroid);
            q.surround = true;
        } else if (q.hasPastures) {
            rc.setIndicatorString(2, "Hunting Pastrs: " + q.soldierCounts.centroid);
            q.hunt = true;
        } else {
            q.soundTower = q.noiseCounts.count == 0;
            q.oneBase = !q.soundTower &&  q.pastrCounts.count == 0;
            q.surround = !q.soundTower && !q.oneBase;
            rc.setIndicatorString(2, "Noise/Sound: " + q.surround + " : " + q.oneBase + " : " + q.soundTower);
        }

        if (ran) {
            spawn.run();
        }

        return true;
    }
}


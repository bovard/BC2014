package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.hq.robot.Qualifier;

/**
 * Created by mpaulson on 1/28/14.
 */
public class HQQualifier extends Behavior {
    Qualifier q;
    public HQQualifier(Qualifier q) {
        super(q);
        this.q = q;
    }
    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {

        // Communications information
        if (q.soldierCounts == null || q.soldierCounts.count < 8 || q.soldierCounts.count > 8 && q.soldierCounts.centroid.distanceSquaredTo(q.info.enemyHq) > 80) {
            rc.setIndicatorString(2, "Surround Technique: " + q.soldierCounts.centroid.distanceSquaredTo(q.info.enemyHq));
            q.surround = true;
            q.groupToSpawn = 0;
        } else if (q.hasPastures) {
            rc.setIndicatorString(2, "Hunting Pastrs: " + q.soldierCounts.centroid);
            q.hunt = true;
            q.groupToSpawn = 0;
        } else {
            q.soundTower = q.noiseCounts.count == 0;
            q.groupToSpawn = 1;
            q.oneBase = !q.soundTower &&  q.pastrCounts.count == 0;
            q.surround = !q.soundTower && !q.oneBase;
            rc.setIndicatorString(2, "Noise/Sound: " + q.surround + " : " + q.oneBase + " : " + q.soundTower);
        }
        return true;
    }
}

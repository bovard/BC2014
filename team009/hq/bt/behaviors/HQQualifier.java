package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import team009.BehaviorConstants;
import team009.bt.behaviors.Behavior;
import team009.hq.robot.Qualifier;

public class HQQualifier extends Behavior {
    Qualifier q;
    private int distToEnemeyHQ;
    public HQQualifier(Qualifier q) {
        super(q);
        this.q = q;
        distToEnemeyHQ = q.info.hq.distanceSquaredTo(q.info.enemyHq);
    }
    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {

        // Communications information
        // should communicate between two different groups

        // group 0
        // if map is too large, create a base
        // otherwise kill pastrs
        // otherwise surroundZero
        // otherwise huddleZero
        q.baseZero = q.surroundZero = q.huntZero = q.huddleZero = false;
        if (q.soldierCountsZero == null || q.soldierCountsZero.count < 4) {
            q.huddleZero = true;
        } else if (distToEnemeyHQ > BehaviorConstants.MAP_DISTANCE_TO_ENEMY_HQ) {
            q.baseZero = true;
        } else if (q.enemyHasPastures) {
            q.huntZero = true;
        } else {
            q.surroundZero = true;
        }



        // group 1
        // if map is too large, create a base
        // huntZero communicating enemies that are not near enemy hq
        // otherwise create a base

//        q.huddleOne = q.baseOne = q.huntComOne = q.baseOne = false;
//        if (q.soldierCountsZero == null || q.soldierCountsZero.count < 4) {
//            q.huddleOne = true;
//        } else if (false && distToEnemeyHQ > BehaviorConstants.MAP_DISTANCE_TO_ENEMY_HQ) {
//            q.baseOne = true;
//        } else if (false) {
//            // TODO: this
//            q.huntComOne = true;
//        } else {
//            q.baseOne = false;
//        }

        return true;
    }
}

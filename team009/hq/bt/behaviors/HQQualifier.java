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
    }
    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        q.baseZero = q.surroundZero = q.huntZero = q.huddleZero = false;
        q.huddleOne = q.baseOne = q.huntComOne = q.baseOne = q.huntPastrOne = false;

        // large map strat
        if (q.info.enemyHqDistance > BehaviorConstants.MAP_DISTANCE_TO_ENEMY_HQ) {
            q.groupToSpawn = 0;

            // group zero
            if (!q.milkInformation.finished) {
                q.huddleZero = true;
            } else if (distToEnemeyHQ > BehaviorConstants.MAP_DISTANCE_TO_ENEMY_HQ) {
                q.baseZero = true;
            }


            // group one

            // if we see there are more than 1 enemy pastr, hunt them!
            if (q.enemyPastrs.arr.length > 1) {
                q.huntPastrOne = true;
                if (q.soldierCountsOne.count < q.soldierCountsZero.count) {
                    q.groupToSpawn = 1;
                }
            }
            // just huddle
            else {
                q.huddleOne = true;
            }

        }

        // small map strategy
        else {
            q.groupToSpawn = 0;

            // group 0
            if (q.enemyHasPastures) {
                q.huntZero = true;
            } else {
                q.surroundZero = true;
            }

            // group 1
            if (q.enemyHasPastures) {
                q.huntPastrOne = true;
            } else if (false) {
                q.huntComOne = true;
            } else {
                q.baseOne = true;
            }

            if (q.soldierCountsZero.count > BehaviorConstants.GROUP_0_SIZE_SMALL_MAP) {
                q.groupToSpawn = 1;
            }


        }


        // Communications information
        // should communicate between two different groups




        return true;
    }
}

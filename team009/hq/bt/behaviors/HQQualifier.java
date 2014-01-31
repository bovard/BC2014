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
            } else {
                q.baseZero = true;
            }


            // group one

            // if we see there are more than 1 enemy pastr, hunt them!
            if (q.enemyPastrs.arr.length > 1) {
                q.huntPastrOne = true;
                if (q.soldierCountsZero != null && q.soldierCountsOne != null && q.soldierCountsOne.count < q.soldierCountsZero.count) {
                    System.out.println("more 0s than 1, spawning a 1");
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
            } else if (q.soldierCountsOne == null || q.soldierCountsOne.count < 4) {
                q.huddleOne = true;
            }  else if (false) {
                // TODO: implement this
                q.huntComOne = true;
            } else {
                q.baseOne = true;
            }

            if (q.soldierCountsOne != null && q.soldierCountsZero.count > BehaviorConstants.GROUP_0_SIZE_SMALL_MAP) {
                System.out.println(q.soldierCountsZero.count + " soldiers in group 0, spawning group 1 now!");
                q.groupToSpawn = 1;
            }


        }


        // Communications information
        // should communicate between two different groups




        return true;
    }
}

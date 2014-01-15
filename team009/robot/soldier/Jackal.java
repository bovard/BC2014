package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.soldier.JackalSelector;
import team009.utils.SmartMapLocationArray;

public class Jackal extends BaseSoldier {
    public SmartMapLocationArray pastrLocs = new SmartMapLocationArray();
    public boolean pastrsExists;

    public Jackal(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new JackalSelector(this);
    }


    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        // reset the array
        pastrLocs.length = 0;
        // sense the pastrs
        MapLocation[] locs = rc.sensePastrLocations(info.enemyTeam);
        // check to make sure they aren't next to the enemy hq
        for (int i = 0; i < locs.length; i++) {
            if (!locs[i].isAdjacentTo(info.enemyHq)) {
                pastrLocs.add(locs[i]);
            }

        }
        //store if we see an enemy pastr
        pastrsExists = pastrLocs.length > 0;

    }
}

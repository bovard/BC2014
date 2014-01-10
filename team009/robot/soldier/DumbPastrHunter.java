package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.DumbPastrHunterSelector;

/**
 * A really dumb pastr hunter created for testing
 */
public class DumbPastrHunter extends BaseSoldier {

    public MapLocation[] pastrLocations = new MapLocation[0];
    public boolean pastrExists = false;

    public DumbPastrHunter(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        pastrLocations = rc.sensePastrLocations(info.enemyTeam);
        pastrExists = pastrLocations.length > 0;
    }

    @Override
    protected Node getTreeRoot() {
        return new DumbPastrHunterSelector(this);
    }
}

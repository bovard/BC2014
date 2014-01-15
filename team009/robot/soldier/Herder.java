package team009.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.soldier.ComRetreatToPasture;
import team009.bt.decisions.soldier.HerderSelector;

public class Herder extends BaseSoldier {
    private MapLocation pastrLocation;
    public boolean enemyComNear = false;
    public MapLocation[] enemyComm = new MapLocation[0];

    public Herder(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        pastrLocation = location;
        treeRoot = getTreeRoot();
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        enemyComm = rc.senseBroadcastingRobotLocations(info.enemyTeam);
        enemyComNear = false;
        for (MapLocation loc : enemyComm) {
            if (loc.distanceSquaredTo(pastrLocation) < ComRetreatToPasture.ENEMY_COM_RANGE_RETREAT) {
                enemyComNear = true;
                break;
            }
        }
    }


    @Override
    protected Node getTreeRoot() {
        return new HerderSelector(this, pastrLocation);
    }
}

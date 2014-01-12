package flow.robot.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.decisions.WolfSelector;
import flow.bt.Node;

public class Wolf extends BaseSoldier {
    public boolean seesEnemy;
    public boolean pastrsExists;
    public MapLocation[] pastrLocs;
    public Robot[] enemies;
    public int group;
    public int type;

    public Wolf(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = enemies.length > 0;

        MapLocation[] locs = rc.sensePastrLocations(info.enemyTeam);
        //store if we see an enemy pastr
        pastrsExists = locs.length > 0;
        pastrLocs = locs;

        //if (Communicator.WriteRound(round)) {
        //    Communicator.WriteTypeAndGroup(rc, type, group);
        //}
    }

    @Override
    protected Node getTreeRoot() {
        return new WolfSelector(this);
    }
}

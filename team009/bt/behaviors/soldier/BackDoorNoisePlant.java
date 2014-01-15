package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import battlecode.common.TerrainTile;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.soldier.BackdoorNoisePlanter;

public class BackDoorNoisePlant extends Behavior {
    private BugMove move;
    private TerrainTile destTerrain;

    public BackDoorNoisePlant(BackdoorNoisePlanter robot) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(robot.getNextWayPoint());
        destTerrain = robot.rc.senseTerrainTile(move.destination);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        int enemyHQ = robot.currentLoc.distanceSquaredTo(robot.info.enemyHq);
        int distToDest = robot.currentLoc.distanceSquaredTo(move.destination);
        if (enemyHQ < robot.currentLoc.distanceSquaredTo(robot.info.hq) && enemyHQ < 300) {
            rc.construct(RobotType.NOISETOWER);
            return true;
        } else if (distToDest < 26 || (distToDest < 100 && !destTerrain.equals(TerrainTile.NORMAL))) {
            move.setDestination(((BackdoorNoisePlanter)robot).getNextWayPoint());
            destTerrain = robot.rc.senseTerrainTile(move.destination);
            move.move();
            return true;
        } else {
            move.move();
            return true;
        }
    }
}

package team009.bt.behaviors.hq;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.robot.hq.HQ;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.SoldierSpawner;

public class HQAction extends Behavior {
    HQ hq;
    MapLocation center = null;
    boolean debugCenter = false;
    MapLocation baseCoverageLocation = null;

    public HQAction(HQ hq) {
        super(hq);
        this.hq = hq;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        if (center == null) {
            _calculateRallyPoint();;
        }

        robot.message += robot.round + " Action ";
        if (debugCenter) {
            robot.message += " (Debug) ";
            if (Clock.getRoundNum() > 400) {
                hq.comDefend(center, BaseSoldier.DEFENDER_GROUP);
            }
        } else {
            MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
            int soldierCount = hq.getCount(SoldierSpawner.SOLDIER_TYPE_DEFENDER, BaseSoldier.DEFENDER_GROUP);

            if (locs.length > 0 && soldierCount > REQUIRED_SOLDIER_COUNT_FOR_ATTACK) {
                hq.comDefend(locs[0], BaseSoldier.DEFENDER_GROUP);
            } else {

                // This will short circuit on both same location or write round.
                robot.message += " (Rally Time! (" + baseCoverageLocation + ") ";
                hq.comClear(BaseSoldier.DEFENDER_GROUP, baseCoverageLocation);
                hq.comDefend(baseCoverageLocation, BaseSoldier.DEFENDER_GROUP);
            }
        }
        return true;
    }

    private void _calculateRallyPoint() throws GameActionException {
        center = new MapLocation(robot.info.width / 2, robot.info.height / 2);
        Direction dir = robot.info.enemyDir;
        for (int i = 0; i < 8; i++) {
            boolean found = true;
            MapLocation curr = robot.info.hq;
            for (int j = 0; j < 3; j++) {
                curr = curr.add(dir);
                TerrainTile tile = rc.senseTerrainTile(curr);
                if (tile == TerrainTile.OFF_MAP || tile == TerrainTile.VOID) {
                    found = false;
                    break;
                }
            }

            if (found) {
                baseCoverageLocation = curr;
                return;
            }
            dir = dir.rotateRight();
        }
    }

    private static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 3;
}

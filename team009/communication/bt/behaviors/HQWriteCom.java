package team009.communication.bt.behaviors;

import battlecode.common.*;
import team009.robot.hq.HQ;
import team009.robot.soldier.BaseSoldier;
import team009.robot.soldier.SoldierSpawner;

public class HQWriteCom extends WriteBehavior {
    HQ hq;
    MapLocation center = null;
    boolean debugCenter = false;
    MapLocation baseCoverageLocation = null;

    public HQWriteCom(HQ robot) {
        super(robot);
        hq = robot;
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
                hq.comAttackPasture(center, BaseSoldier.DEFENDER_GROUP);
            }
        } else {
            MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
            int soldierCount = hq.getCount(SoldierSpawner.SOLDIER_TYPE_DEFENDER, BaseSoldier.DEFENDER_GROUP);

            if (locs.length > 0 && soldierCount > REQUIRED_SOLDIER_COUNT_FOR_ATTACK) {
                robot.message += " (Rally Time! (" + locs[0] + ") ";
                hq.comAttackPasture(locs[0], BaseSoldier.DEFENDER_GROUP);
            } else {

                // This will short circuit on both same location or write round.
                hq.comClear(BaseSoldier.DEFENDER_GROUP, baseCoverageLocation);
                hq.comReturnHome(baseCoverageLocation, BaseSoldier.DEFENDER_GROUP);
            }
        }
        return true;
    }

    private void _calculateRallyPoint() throws GameActionException {
        center = new MapLocation(robot.info.width / 2, robot.info.height / 2);
        MapLocation hq = robot.info.hq;
        Direction dir = robot.info.enemyDir;

        for (int i = 0; i < 8; i++) {
            boolean found = true;
            MapLocation curr = hq;
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

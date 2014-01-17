package team009.communication.bt.behaviors;

import battlecode.common.*;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;
import team009.robot.soldier.SoldierSpawner;

public class HQWriteCom extends WriteBehavior {
    HQ hq;
    MapLocation center = null;
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

        MapLocation[] locs = rc.sensePastrLocations(robot.info.enemyTeam);
        int soldierCount = hq.getCount(TeamRobot.TOY_GROUP);

        if (locs.length > 0 && soldierCount > REQUIRED_SOLDIER_COUNT_FOR_ATTACK) {
            hq.comAttackPasture(locs[0], TeamRobot.TOY_GROUP);
        } else {

            hq.comClear(TeamRobot.TOY_GROUP, baseCoverageLocation);
            hq.comReturnHome(baseCoverageLocation, TeamRobot.TOY_GROUP);
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

package team009.communication.bt.behaviors;

import battlecode.common.*;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;
import team009.robot.soldier.SoldierSpawner;

public class HQWriteCom extends WriteBehavior {
    HQ hq;
    MapLocation center = null;
    MapLocation baseCoverageLocation = null;
    boolean capturing = false;

    public HQWriteCom(HQ robot) {
        super(robot);
        hq = robot;
    }

    @Override
    public boolean run() throws GameActionException {
        if (center == null) {
            _calculateRallyPoint();;
        }

        int toyCount0 = hq.getCount(0);
        int toyCount1 = hq.getCount(1);
        boolean enough0 = toyCount0 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean enough1 = toyCount1 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        rc.setIndicatorString(0, "Capture: " + (capturing ? "I am capturing" : "i am not"));

        if (capturing) {
            hq.comCapture(hq.bestRegenLoc, 0);

            // Can we send group 1 out?
            if (enough1 && hq.hasPastures) {
                hq.comAttackPasture(hq.pastures[0], 1);
            } else {
                hq.comDefend(getDefendingLocation(hq.bestRegenLoc), 1);
            }
        } else if (hq.hasPastures) {
            if (hq.pastures.length > 1) {
                if (enough0) {
                    hq.comAttackPasture(hq.pastures[0], 0);
                    if (!enough1) {
                        hq.comAttackPasture(hq.pastures[0], 1);
                    }
                }
                if (enough1) {
                    hq.comAttackPasture(hq.pastures[1], 1);
                    if (!enough0) {
                        hq.comAttackPasture(hq.pastures[0], 0);
                    }
                }
            } else if (hq.pastures.length == 1) {
                hq.comAttackPasture(hq.pastures[0], 1);
                hq.comAttackPasture(hq.pastures[0], 0);
            }
        } else {
            if (enough0) {
                hq.comCapture(hq.bestRegenLoc, 0);

                // send 1 to defend no matter what.
                hq.comDefend(hq.bestRegenLoc, 1);
                capturing = true;
            } else {
                hq.comClear(0, baseCoverageLocation);
                hq.comClear(1, baseCoverageLocation);
                hq.comReturnHome(baseCoverageLocation, 0);
                hq.comReturnHome(baseCoverageLocation, 1);
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

    private MapLocation getDefendingLocation(MapLocation loc) {
        Direction dir = robot.info.enemyDir;
        int rotates = (int)(Math.random() * 8);
        boolean right = ((int)(Math.random() * 2) == 1);
        for (int i = 0; i < rotates; i++) {
            dir = right ? dir.rotateRight() : dir.rotateLeft();
        }

        for (int i = 0; i < 8; i++) {
            MapLocation newLoc = loc.add(dir, 2);
            TerrainTile tile = rc.senseTerrainTile(newLoc);
            if (tile != TerrainTile.OFF_MAP || tile != TerrainTile.VOID) {
                return newLoc;
            }
        }
        return loc;
    }

    private static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 7;
}

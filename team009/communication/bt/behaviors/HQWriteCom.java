package team009.communication.bt.behaviors;

import battlecode.common.*;
import team009.navigation.BugMove;
import team009.robot.hq.HQ;

public class HQWriteCom extends WriteBehavior {
    HQ hq;
    MapLocation center = null;
    MapLocation bestCoverageLocation = null;
    boolean capturing = false;
    int defendingGroup = -1;
    BugMove move;

    public HQWriteCom(HQ robot) {
        super(robot);
        hq = robot;
        move = new BugMove(robot);

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
        boolean combinedEnough = toyCount0 + toyCount1 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;

        rc.setIndicatorString(0, "Capture: " + (capturing ? "I am capturing" : "i am not"));

        if (capturing) {
            hq.comCapture(hq.bestRegenLoc, 0);

            // Can we send group 1 out?
            boolean allIn = rc.senseTeamMilkQuantity(robot.info.enemyTeam) > rc.senseTeamMilkQuantity(robot.info.myTeam);
            if (!combinedEnough) {
                if (rc.sensePastrLocations(robot.info.myTeam).length > 0) {
                    if (defendingGroup == 0) {
                        hq.comDefend(hq.bestRegenLoc, 0);
                        hq.comCapture(hq.bestRegenLoc, 1);
                    } else if (defendingGroup == 1) {
                        hq.comCapture(hq.bestRegenLoc, 0);
                        hq.comDefend(hq.bestRegenLoc, 1);
                    } else {
                        hq.comReturnHome(bestCoverageLocation, 0);
                        hq.comReturnHome(bestCoverageLocation, 1);
                        capturing = false;
                    }
                } else {
                    hq.comReturnHome(bestCoverageLocation, 0);
                    hq.comReturnHome(bestCoverageLocation, 1);
                    capturing = false;
                }
            } else {
                if (hq.hasPastures) {
                    if (allIn) {
                        hq.comAttackPasture(hq.pastures.arr[0], 0);
                        hq.comAttackPasture(hq.pastures.arr[0], 1);
                    } else if (defendingGroup == 0 && enough0) {
                        hq.comAttackPasture(hq.pastures.arr[0], 0);
                    } else if (defendingGroup == 1 && enough1) {
                        hq.comAttackPasture(hq.pastures.arr[0], 1);
                    }
                } else {
                    hq.comDefend(getDefendingLocation(hq.bestRegenLoc), 1);
                }
            }


        } else if (hq.hasPastures && combinedEnough) {
            if (enough0 && enough1) {
                hq.comAttackPasture(hq.pastures.arr[0], 0);
                if (hq.pastures.length > 1) {
                    hq.comAttackPasture(hq.pastures.arr[1], 1);
                }
            } else {
                hq.comAttackPasture(hq.pastures.arr[0], 0);
                hq.comAttackPasture(hq.pastures.arr[0], 1);
            }
        } else {
            if (enough0 || enough1) {
                if (enough0) {
                    hq.comCapture(hq.bestRegenLoc, 0);
                    hq.comDefend(hq.bestRegenLoc, 1);
                    defendingGroup = 1;
                } else {
                    hq.comDefend(hq.bestRegenLoc, 0);
                    hq.comCapture(hq.bestRegenLoc, 1);
                    defendingGroup = 0;
                }

                // send 1 to defend no matter what.
                capturing = true;
            } else {
                hq.comReturnHome(bestCoverageLocation, 0);
                hq.comReturnHome(bestCoverageLocation, 1);
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
                bestCoverageLocation = curr;
                return;
            }
            dir = dir.rotateRight();
        }

        move.setDestination(robot.info.enemyHq);
        dir = move.calcMove();
        MapLocation homeLoc = robot.info.hq.add(dir);
        for (int i = 0; i < 8; i++) {
            MapLocation next = homeLoc.add(dir);
            TerrainTile tile = rc.senseTerrainTile(next);
            if (tile != TerrainTile.VOID || tile != TerrainTile.OFF_MAP) {
                bestCoverageLocation = next;
                return;
            } else {
                dir = dir.rotateRight();
            }
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

    private static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 5;
}

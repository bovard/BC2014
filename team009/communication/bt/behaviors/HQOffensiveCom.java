package team009.communication.bt.behaviors;

import battlecode.common.*;
import team009.navigation.BugMove;
import team009.robot.hq.Offensive;

public class HQOffensiveCom extends WriteBehavior {
    Offensive hq;
    MapLocation center = null;
    MapLocation bestCoverageLocation = null;
    int defendingGroup = -1;
    BugMove move;

    public HQOffensiveCom(Offensive robot) {
        super(robot);
        hq = robot;
        move = new BugMove(robot);

    }

    @Override
    public boolean run() throws GameActionException {
        if (center == null) {
            _calculateRallyPoint();;
        }

        if (hq.oneBase) {
            hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
            hq.comDefend(hq.milkInformation.targetBoxes[0].bestSpot, 1);
        }

        if (hq.hunt0 && hq.hunt1) {
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
            hq.comAttackPasture(hq.enemyPastrs.arr[1], 1);
        } else if (hq.hunt0) {
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 0);
            hq.comAttackPasture(hq.enemyPastrs.arr[0], 1);
        }

        if (hq.hudle) {
            hq.comReturnHome(bestCoverageLocation, 0);
            hq.comReturnHome(bestCoverageLocation, 1);
        }

        if (hq.chase0) {
            hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
        }

        if (hq.chase1) {
            hq.comCapture(hq.milkInformation.targetBoxes[1].bestSpot, 1);
        }

//        int toyCount0 = hq.getCount(0);
//        int toyCount1 = hq.getCount(1);
//        boolean enough0 = toyCount0 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
//        boolean enough1 = toyCount1 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
//        boolean combinedEnough = toyCount0 + toyCount1 >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
//
//        // TODO: Add in a back and forth (going from best spot 1 to best spot 2) you know, standard starcraft stuff.
//        if (capturing) {
//            hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
//
//            // Can we send group 1 out?
//            boolean allIn = rc.senseTeamMilkQuantity(robot.info.enemyTeam) > rc.senseTeamMilkQuantity(robot.info.myTeam);
//            if (!combinedEnough) {
//                if (rc.sensePastrLocations(robot.info.myTeam).length > 0) {
//                    if (defendingGroup == 0) {
//                        hq.comDefend(hq.milkInformation.targetBoxes[0].bestSpot, 0);
//                        hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 1);
//                    } else if (defendingGroup == 1) {
//                        hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
//                        hq.comDefend(hq.milkInformation.targetBoxes[0].bestSpot, 1);
//                    } else {
//                        hq.comReturnHome(bestCoverageLocation, 0);
//                        hq.comReturnHome(bestCoverageLocation, 1);
//                        capturing = false;
//                    }
//                } else {
//                    hq.comReturnHome(bestCoverageLocation, 0);
//                    hq.comReturnHome(bestCoverageLocation, 1);
//                    capturing = false;
//                }
//            } else {
//                if (hq.hasPastures) {
//                    if (allIn) {
//                        hq.comAttackPasture(hq.pastures.arr[0], 0);
//                        hq.comAttackPasture(hq.pastures.arr[0], 1);
//                    } else if (defendingGroup == 0 && enough0) {
//                        hq.comAttackPasture(hq.pastures.arr[0], 0);
//                    } else if (defendingGroup == 1 && enough1) {
//                        hq.comAttackPasture(hq.pastures.arr[0], 1);
//                    }
//                } else {
//                    hq.comDefend(getDefendingLocation(hq.milkInformation.targetBoxes[0].bestSpot), 1);
//                }
//            }
//
//
//        } else if (hq.hasPastures && combinedEnough) {
//            if (enough0 && enough1) {
//                hq.comAttackPasture(hq.pastures.arr[0], 0);
//                if (hq.pastures.length > 1) {
//                    hq.comAttackPasture(hq.pastures.arr[1], 1);
//                }
//            } else {
//                hq.comAttackPasture(hq.pastures.arr[0], 0);
//                hq.comAttackPasture(hq.pastures.arr[0], 1);
//            }
//        } else {
//            if (hq.milkInformation.finished && (enough0 || enough1)) {
//                if (enough0) {
//                    hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 0);
//                    hq.comDefend(hq.milkInformation.targetBoxes[0].bestSpot, 1);
//                    defendingGroup = 1;
//                } else {
//                    hq.comDefend(hq.milkInformation.targetBoxes[0].bestSpot, 0);
//                    hq.comCapture(hq.milkInformation.targetBoxes[0].bestSpot, 1);
//                    defendingGroup = 0;
//                }
//
//                // send 1 to defend no matter what.
//                capturing = true;
//            } else {
//                hq.comReturnHome(bestCoverageLocation, 0);
//                hq.comReturnHome(bestCoverageLocation, 1);
//            }
//        }
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

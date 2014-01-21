package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;
import team009.BehaviorConstants;
import team009.robot.hq.HQ;

public class CheesePostProcess {
    private RobotController rc;
    private RobotInformation info;
    private HQ hq;
    private MilkInformation milkInformation;
    private boolean mapDone = false;
    private boolean distanceCheck = false;
    private int cheeseI = 0;
    private int cheeseJ = 0;
    private int cheeseILen = 0;
    private int cheeseJLen = 0;
    private int cheeseJStart = 0;
    private int radius = (int)Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared) - 1;
    private int halfRadius = radius / 3;
    private double milkTotal = 0;
    private Direction currDir = Direction.NORTH;
    private int blockedCount = 0;

    public boolean finished = false;
    public boolean cheese = false;
    public MapLocation soundTower;
    public MapLocation pasture;

    public CheesePostProcess(HQ hq, MilkInformation milkInformation) {
        rc = hq.rc;
        info = hq.info;
        this.hq = hq;
        this.milkInformation = milkInformation;

        cheeseI = info.hq.x - radius;
        cheeseJStart = info.hq.y - radius;
        cheeseILen = cheeseI + radius;
        cheeseJLen = cheeseJ + radius;

        if (cheeseI < 0) {
            cheeseI = 0;
        }

        if (cheeseJStart < 0) {
            cheeseJStart = 0;
        }

        if (cheeseILen < info.width) {
            cheeseJ = info.width;
        }

        if (cheeseJLen < info.height) {
            cheeseJLen = info.height;
        }

        pasture = info.hq.add(info.enemyDir.rotateLeft().rotateLeft());
        soundTower = info.hq.add(info.enemyDir.rotateRight().rotateRight());

    }

    public void calc() {
        if (finished) {
            return;
        }

        if (!distanceCheck && info.enemyHqDistance < BehaviorConstants.CHEESE_HQ_ENEMY_MINIMUM_DISTANCE) {
            Direction dir = info.enemyDir;
            MapLocation curr = info.hq.add(dir);
            MapLocation enemy = info.enemyHq;
            boolean wall = false;

            while (!curr.equals(enemy)) {
                TerrainTile tile = rc.senseTerrainTile(curr);

                if (tile == TerrainTile.VOID) {
                    wall = true;
                    break;
                }
                dir = curr.directionTo(enemy);
                curr = curr.add(dir);
            }

            distanceCheck = true;
            if (!wall) {
                finished = true;
                return;
            }
        }

        int rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / 20;
        int k = 0;
        while (!finished && !mapDone && k < rounds) {
            for (; cheeseI < cheeseILen; cheeseI++, k++) {
                for (cheeseJ = cheeseJStart; cheeseJ < cheeseJLen; cheeseJ++, k++) {
                    milkTotal += milkInformation.milks[cheeseI][cheeseJ];
                }
            }
            mapDone = cheeseI == cheeseILen;
        }

        rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / 20;
        k = 0;

        MapLocation myLoc = hq.currentLoc;
        MapLocation curr = myLoc;
        while (k < rounds && mapDone && !finished) {
            for (int i = 0; i < halfRadius; i++) {
                curr = curr.add(currDir);
                TerrainTile tile = rc.senseTerrainTile(curr);
                if (tile == TerrainTile.OFF_MAP || tile == TerrainTile.VOID) {
                    blockedCount++;
                    break;
                }
            }

            currDir = currDir.rotateRight();
            curr = myLoc;
            finished = currDir == Direction.NORTH;
        }

        cheese = finished && milkTotal > BehaviorConstants.CHEESE_MILK_MINIMUM && blockedCount < 3;
    }

}

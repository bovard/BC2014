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
    private int[][] map;
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

        if (map == null) {
            map = hq.map.map;
        }
        int[][] map = this.map;

        if (!distanceCheck && info.enemyHqDistance < BehaviorConstants.CHEESE_HQ_ENEMY_MINIMUM_DISTANCE) {
            Direction dir = info.enemyDir;
            MapLocation curr = info.hq.add(dir);
            MapLocation enemy = info.enemyHq;
            boolean wall = false;

            while (!curr.equals(enemy)) {
                if (map[curr.x][curr.y] == 1) {
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

        int rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / (40 + 5 * cheeseJLen - cheeseJStart);
        int k = 0;
        int j, jStart = cheeseJStart, jLen = cheeseJLen;
        int cheeseI = this.cheeseI;
        while (!mapDone && !mapDone && k < rounds) {
            for (; cheeseI < cheeseILen; cheeseI++, k++) {
                for (j = jStart; j < jLen; j++) {
                    milkTotal += milkInformation.milks[cheeseI][j];
                }
            }
            mapDone = cheeseI == cheeseILen;
        }
        this.cheeseI = cheeseI;

        if (!mapDone) {
            return;
        }

        rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / 50;
        k = 0;

        MapLocation myLoc = hq.currentLoc;
        MapLocation curr = myLoc;
        int width = info.width;
        int height = info.height;
        while (k < rounds && mapDone && !finished) {
            for (int i = 0; i < halfRadius; i++) {
                curr = curr.add(currDir);
                if (curr.x < 0 || curr.y < 0 || curr.x >= width || curr.y >= height || map[curr.x][curr.y] == 1) {
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

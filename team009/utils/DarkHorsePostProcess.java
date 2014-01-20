package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;
import team009.robot.hq.HQ;

public class DarkHorsePostProcess {
    private RobotController rc;
    private RobotInformation info;
    private HQ hq;
    private MilkInformation milkInformation;
    private boolean mapDone = false;
    private int darkI = 0;
    private int darkJ = 0;
    private int darkILen = 0;
    private int darkJLen = 0;
    private int radius = (int)Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared) - 2;
    private double milkTotal = 0;
    private Direction currDir = Direction.NORTH;
    private int blockedCount = 0;

    public boolean finished = false;
    public boolean darkHorse = false;

    public DarkHorsePostProcess(HQ hq, MilkInformation milkInformation) {
        this.rc = hq.rc;
        this.info = hq.info;
        this.hq = hq;
        this.milkInformation = milkInformation;

        darkI = info.hq.x - radius;
        darkJ = info.hq.y - radius;
        darkILen = darkI + radius;
        darkJLen = darkJ + radius;

        if (darkI < 0) {
            darkI = 0;
        }

        if (darkJ < 0) {
            darkJ = 0;
        }

        if (darkILen < info.width) {
            darkJ = info.width;
        }

        if (darkJLen < info.height) {
            darkJLen = info.height;
        }
    }

    public void calc() {
        int rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / 20;
        int k = 0;
        while (!finished && !mapDone && k < rounds) {
            for (; darkI < darkILen; darkI++, k++) {
                for (; darkJ < darkJLen; darkJ++, k++) {
                    milkTotal += milkInformation.milks[darkI][darkJ];
                }
            }
            mapDone = darkI == darkILen;
        }

        rounds = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum() + 200)) / 20;
        k = 0;

        MapLocation myLoc = hq.currentLoc;
        MapLocation curr = myLoc;
        while (k > rounds && mapDone && !finished) {
            for (int i = 0; i < 5; i++) {
                curr = curr.add(this.currDir);
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

        darkHorse = finished && milkTotal > DARK_HORSE_MILK_MINIMUM && blockedCount < 3;
    }

    public static final int DARK_HORSE_MILK_MINIMUM = 100;
}

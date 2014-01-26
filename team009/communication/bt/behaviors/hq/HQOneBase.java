package team009.communication.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.robot.hq.Offensive;

public class HQOneBase extends WriteBehavior {
    Offensive hq;

    public HQOneBase(Offensive off) {
        super(off);
        hq = off;
    }

    @Override
    public boolean run() throws GameActionException {
        // TODO: Better location
        MapLocation bestSpot = hq.milkInformation.oneBaseBestSpot;
        MapLocation noiseLoc = bestSpot.add(Direction.NORTH);

        boolean hasSound = false;
        for (int i = 0; i < hq.noiseLocations.length; i++) {
            if (hq.noiseLocations.arr[i].equals(noiseLoc)) {
                hasSound = true;
                break;
            }
        }

        if (hasSound) {
            System.out.println("Sending out information.");
            hq.comCapture(bestSpot, 0);
        } else {
            hq.comSoundTower(noiseLoc, 0);
        }
        return true;
    }
}

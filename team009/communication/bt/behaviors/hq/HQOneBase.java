package team009.communication.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.bt.behaviors.WriteBehavior;
import team009.hq.robot.Qualifier;

public class HQOneBase extends WriteBehavior {
    Qualifier hq;

    public HQOneBase(Qualifier off) {
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
            System.out.println("BestSpot: " + bestSpot);
            hq.comCapture(bestSpot, 0);
        } else {
            System.out.println("Noise: " + noiseLoc);
            hq.comSoundTower(noiseLoc, 0);
        }
        return true;
    }
}

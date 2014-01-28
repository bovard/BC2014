package team009.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.hq.HQ;
import team009.utils.*;

public abstract class HQPreprocessor extends HQ {


    public MilkInformation milkInformation;
    public MapPreProcessor map;
    public RallyPointProcessor rally;
    protected boolean finishedPostCalc = false;
    private int donePath = 0;

    public HQPreprocessor(RobotController rc, RobotInformation info) {
        super(rc, info);
        map = new MapPreProcessor(this);
        milkInformation = new MilkInformation(this);
        rally = new RallyPointProcessor(this);
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();

        if (!rally.finished) {
            rally.calc();
        }

        if (!map.finished) {
            map.calc();
            return;
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }

        if (donePath < 2) {
            System.out.println("Starting AStar init at " + Clock.getBytecodeNum());
            AStar a = new AStar(map.coarseMap, map.minValue);
            System.out.println("Finishing AStar init at " + Clock.getBytecodeNum());
            System.out.println("MapHas: " + map.coarseDivisor);
            MapLocation hq = rc.getLocation();
            MapLocation eHQ = rc.senseEnemyHQLocation();
            int startSquare = a.mapLocationToSquareID(hq, map.coarseDivisor);
            int endSquare = a.mapLocationToSquareID(eHQ, map.coarseDivisor);
            System.out.println("Going from " + startSquare + " to " + endSquare);
            System.out.println("Starting AStar getNextSquare at " + Clock.getBytecodeNum());
            a.getNextSquare(startSquare, endSquare);
            System.out.println("Ending AStar getNextSquare at " + Clock.getBytecodeNum());
            donePath++;
            return;
        }


        finishedPostCalc = true;
    }
}

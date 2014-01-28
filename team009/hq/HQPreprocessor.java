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
    private boolean initStar = false;
    private AStar a;

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
        if (!initStar) {
            initStar = true;
            System.out.println("Starting AStar init at " + Clock.getBytecodeNum());
            a = new AStar(map.coarseMap, map.minValue, map.coarseWidth, map.coarseWidth);
            System.out.println("Finishing AStar init at " + Clock.getBytecodeNum());
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }

        if (donePath < 2) {
            try {
                System.out.println("MapHas: " + map.coarseDivisor);
                MapLocation hq = rc.getLocation();
                MapLocation eHQ = rc.senseEnemyHQLocation();
                int startSquare = a.mapLocationToSquareID(hq, map.coarseHeight, map.coarseWidth);
                int endSquare = a.mapLocationToSquareID(eHQ, map.coarseHeight, map.coarseWidth);
                System.out.println("Going from " + startSquare + " to " + endSquare);
                int startRound = Clock.getRoundNum();
                System.out.println("Starting AStar getNextSquare at " + Clock.getBytecodeNum());
                a.getNextSquare(startSquare, endSquare);
                System.out.println("Ending AStar getNextSquare at " + Clock.getBytecodeNum());
                System.out.println("Ended in " + (Clock.getRoundNum() - startRound) + " Rounds");
                donePath++;
                return;
            } catch (Exception e) {
                e.printStackTrace();
                donePath++;
                return;
            }
        }


        finishedPostCalc = true;
    }
}

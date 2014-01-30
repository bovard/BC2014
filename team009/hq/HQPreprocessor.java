package team009.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
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
            a = new AStar(map.coarseMap, map.minValue, map.coarseWidth, map.coarseHeight);
            System.out.println("Finishing AStar init at " + Clock.getBytecodeNum());
            return;
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }



        finishedPostCalc = true;
    }
}

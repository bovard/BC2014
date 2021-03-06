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
    private boolean initStar = false;
    public AStar a = null;


    public MapLocation from;
    public MapLocation to;

    public HQPreprocessor(RobotController rc, RobotInformation info) {
        super(rc, info);
        map = new MapPreProcessor(this);
        milkInformation = new MilkInformation(this);
        rally = new RallyPointProcessor(this);

        from = rc.senseHQLocation();
        to = rc.senseEnemyHQLocation();
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();

        if (!rally.finished) {
            rally.calc();
        }

        if (!map.finished) {
            map.calc();
            System.out.println("Map: " + map.finished);
            return;
        }


//        if (!milkInformation.finished) {
//            System.out.println("Milk: " + milkInformation.finished);
//            milkInformation.calc();
//            return;
//        }

        if (!initStar) {
            initStar = true;
            System.out.println("Starting AStar init at " + Clock.getBytecodeNum());
            a = new AStar(map.coarseMap, map.minValue, map.coarseWidth, map.coarseHeight);
            System.out.println("Finishing AStar init at " + Clock.getBytecodeNum());
            return;
        }

        // if we have an aStar and we are processing, process away!
        if (to != null && from != null) {
            MapLocation done = a.getNextWayPoint(from, to);
            // the Map Location will be non null if it's done processing!
            if (done != null) {
                from = null;
                to = null;
            }
        }
    }
}

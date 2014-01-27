package team009.robot.hq;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.utils.*;

public abstract class HQPreprocessor extends HQ {


    public MilkInformation milkInformation;
    public MapPreProcessor map;
    public RallyPointProcessor rally;
    protected boolean finishedPostCalc = false;

    HQPreprocessor(RobotController rc, RobotInformation info) {
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

        finishedPostCalc = true;
    }
}

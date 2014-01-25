package team009.robot.hq;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.utils.ChaseStrategyUtil;
import team009.utils.CheesePostProcess;
import team009.utils.MapPreProcessor;
import team009.utils.MilkInformation;

/**
 * Created by mpaulson on 1/25/14.
 */
public abstract class HQPreprocessor extends HQ {


    public MilkInformation milkInformation;
    public MapPreProcessor map;
    protected boolean finishedPostCalc = false;

    HQPreprocessor(RobotController rc, RobotInformation info) {
        super(rc, info);
        map = new MapPreProcessor(this);
        milkInformation = new MilkInformation(this);
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();

        if (!map.finished) {
            map.calc();
            return;
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }

        finishedPostCalc = milkInformation.finished;
    }
}

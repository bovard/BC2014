package team009.robot.hq;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.hq.OffensiveSelector;
import team009.communication.bt.HQCom;
import team009.utils.DarkHorsePostProcess;
import team009.utils.MilkInformation;

public class Offensive extends HQ {
    // Default behavior is one base and both groups defend it
    // If both groups get big, the second can break away and hunt.
    public boolean hudle = false;
    public boolean oneBase = false;
    public boolean hunt0 = false;
    public boolean hunt1 = false;
    public boolean chase0 = false;
    public boolean chase1 = false;

    public int group0Count;
    public int group1Count;
    public boolean finishedPostCalc = false;
    private boolean largeMap = false;
    private boolean mediumMap = false;

    public MilkInformation milkInformation;
    public DarkHorsePostProcess darkHorse;

    public Offensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new HQCom(this);
        largeMap = info.width * info.height > 1200;
        mediumMap = !largeMap && info.width * info.height > 800;
        milkInformation = new MilkInformation(rc, info);
        darkHorse = new DarkHorsePostProcess(this, milkInformation);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        // determines the base behavior to run
        group0Count = getCount(0);
        group1Count = getCount(1);

        // Gets the different groups
        boolean enough0Attack = group0Count >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean enough1Attack = group1Count >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean combinedEnoughAttack = group0Count + group1Count >= REQUIRED_SOLDIER_COUNT_FOR_ATTACK;

        if (largeMap) {
            // TODO: Large Map Strategy?
            chase0 = group0Count > REQUIRED_SOLDIER_COUNT_FOR_CHASE;
            chase1 = group1Count > REQUIRED_SOLDIER_COUNT_FOR_CHASE;
        }

        else if (mediumMap) {
            // TODO: How do we do hunt0 and oneBase?
            if (!enough0Attack && !enough1Attack && combinedEnoughAttack) {
                hunt0 = hasPastures;
                hunt1 = false;
                oneBase = !hunt0;
            }

            if (hasPastures && combinedEnoughAttack && (enough0Attack || enough1Attack)) {
                hunt0 = enough0Attack;
                hunt1 = enough1Attack && enemyPastrs.length > 1;
            }

            // wtf do we do?
            hudle = !hunt0 && !hunt1 && !oneBase;
        }

        else {
            hunt0 = combinedEnoughAttack;
        }
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();
        if (darkHorse.finished) {
            return;
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }

        darkHorse.calc();
        rc.setIndicatorString(0, "DarkHorse: " + "Finished: " + darkHorse.darkHorse);
        finishedPostCalc = true;
    }

    @Override
    protected Node getTreeRoot() {
        return new OffensiveSelector(this);
    }


    private static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 5;
    private static final int REQUIRED_SOLDIER_COUNT_FOR_CHASE = 3;
}

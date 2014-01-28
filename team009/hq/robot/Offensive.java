package team009.hq.robot;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQPreprocessor;
import team009.hq.bt.selectors.OffensiveSelector;
import team009.utils.MilkInformation;

public class Offensive extends HQPreprocessor {
    // Default behavior is one base and both groups defend it
    // If both groups get big, the second can break away and hunt.
    public boolean huddle = false;
    public boolean oneBase = false;
    public boolean soundTower = false;
    public boolean hunt = false;
    public boolean surround = false;
    public int group0Count;

    private int milkingSpot = 0;

    public Offensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
//        comRoot = new HQCom(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        // determines the base behavior to run
        group0Count = getCount(0);

        // Gets the different groups
        boolean enough = group0Count >= BehaviorConstants.HQ_REQUIRED_SOLDIER_COUNT_FOR_ATTACK;

        hunt = enough && hasPastures;
        soundTower = !surround && enough && milkInformation.finished && noiseLocations.length == 0;
        oneBase = !surround && enough && !soundTower && round > BehaviorConstants.ONE_BASE_ROUND_START && pastrLocations.length == 0;
        huddle = !surround && !hunt;

        // TODO: How to get out of oneBase?
//        soundTower = !surround && enough && milkInformation.finished && noiseLocations.length == 0;
//        hunt = !surround && enough && hasPastures && !oneBase;
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();
    }

    public MapLocation getNextMilkingSpot() {
        MilkInformation.Box box = milkInformation.targetBoxes[milkingSpot];

        milkingSpot = (milkingSpot + 1) % milkInformation.targetBoxes.length;
        return box.bestSpot;
    }

    @Override
    protected Node getTreeRoot() {
        return new OffensiveSelector(this);
    }
}

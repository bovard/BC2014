package team009.robot.hq;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.hq.OffensiveSelector;
import team009.communication.bt.HQCom;
import team009.utils.ChaseStrategyUtil;
import team009.utils.DarkHorsePostProcess;
import team009.utils.MilkInformation;

public class Offensive extends HQ {
    // Default behavior is one base and both groups defend it
    // If both groups get big, the second can break away and hunt.
    public boolean huddle = false;
    public boolean oneBase = false;
    public boolean hunt0 = false;
    public boolean hunt1 = false;
    public boolean chase0 = false;
    public boolean chase1 = false;
    public boolean dark = false;

    public int group0Count;
    public int group1Count;
    public boolean finishedPostCalc = false;

    public MilkInformation milkInformation;
    public DarkHorsePostProcess darkHorse;
    public ChaseStrategyUtil chaseStrategy;

    public MapLocation center;
    public MapLocation bestCoverageLocation;

    private boolean largeMap = false;
    private boolean mediumMap = false;

    public Offensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new HQCom(this);
        largeMap = info.width * info.height > BehaviorConstants.LARGE_MAP_MINIMUM_AREA;
        mediumMap = !largeMap && info.width * info.height > BehaviorConstants.MEDIUM_MAP_MINIMUM_AREA;
        milkInformation = new MilkInformation(rc, info);
        darkHorse = new DarkHorsePostProcess(this, milkInformation);
        chaseStrategy = new ChaseStrategyUtil(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        // determines the base behavior to run
        group0Count = getCount(0);
        group1Count = getCount(1);

        // Gets the different groups
        boolean enough0Attack = group0Count >= BehaviorConstants.REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean enough1Attack = group1Count >= BehaviorConstants.REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean combinedEnoughAttack = group0Count + group1Count >= BehaviorConstants.REQUIRED_SOLDIER_COUNT_FOR_GROUP_ATTACK;

        // TODO: Dark horse
        if (BehaviorConstants.DARK_HORSE_ENABLED && darkHorse.darkHorse) {
            dark = true;
        }

        // TODO: Chase strat
        if (BehaviorConstants.CHASE_ENABLED && chaseStrategy.chase && (!BehaviorConstants.CHASE_CAN_CANCEL_FOR_HUNT || !hasPastures) &&
                Clock.getRoundNum() > BehaviorConstants.CHASE_MINIMUM_ROUND_NUMBER) {
            chase0 = group0Count > BehaviorConstants.CHASE_REQUIRED_SOLDIER_COUNT;
            chase1 = group1Count > BehaviorConstants.CHASE_REQUIRED_SOLDIER_COUNT;
        }


        // TODO: Large Map Strategy?
        else if (largeMap && false) {

        }

        // TODO: How do we incorporate dark horse?
        else if (mediumMap || largeMap) {
            // TODO: How do we do hunt0 and oneBase?
            hunt0 = hasPastures && combinedEnoughAttack;
            hunt1 = hunt0 && enough0Attack && enough1Attack;

            if (combinedEnoughAttack && !hunt0) {
                oneBase = true;
            }
        }

        else {
            hunt0 = combinedEnoughAttack && hasPastures;
        }
        huddle = !hunt0 && !hunt1 && !chase0 && !chase1 && !oneBase;
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();
        if (darkHorse.finished) {
            return;
        }

        if (bestCoverageLocation == null) {
            _calculateRallyPoint();
        }

        if (!milkInformation.finished) {
            milkInformation.calc();
            return;
        }

        if (!chaseStrategy.finished) {
            chaseStrategy.calc();
            return;
        }

        darkHorse.calc();

        if (darkHorse.finished) {
            rc.setIndicatorString(0, "DarkHorse: " + "Finished: " + darkHorse.darkHorse);
            finishedPostCalc = true;
        }
    }

    public MapLocation getNextMilkingSpot() {
        // TODO: Switch this around?
        return milkInformation.targetBoxes[0].bestSpot;
    }

    @Override
    protected Node getTreeRoot() {
        return new OffensiveSelector(this);
    }

    private void _calculateRallyPoint() throws GameActionException {
        int halfWidth = info.width / 2;
        center = new MapLocation(halfWidth, info.height / 2);
        MapLocation hq = info.hq;
        Direction dir = info.enemyDir;

        for (int i = 0; i < 8; i++) {
            boolean found = true;
            MapLocation curr = hq;
            for (int j = 0; j < 3; j++) {
                curr = curr.add(dir);
                TerrainTile tile = rc.senseTerrainTile(curr);
                if (tile == TerrainTile.OFF_MAP || tile == TerrainTile.VOID) {
                    found = false;
                    break;
                }
            }

            if (found) {
                bestCoverageLocation = curr;
                return;
            }
            dir = dir.rotateRight();
        }

        Direction toHome = center.directionTo(info.hq);
        MapLocation curr = center;
        for (int i = 0; i < halfWidth; i++) {
            curr = curr.add(toHome);
            TerrainTile tile = rc.senseTerrainTile(curr);
            if (tile != TerrainTile.VOID || tile != TerrainTile.OFF_MAP) {
                bestCoverageLocation = curr;
                break;
            }
        }

        // TODO: Can there actual errors?
    }
}

package team009.robot.hq;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.hq.OffensiveSelector;
import team009.communication.bt.HQCom;
import team009.utils.ChaseStrategyUtil;
import team009.utils.CheesePostProcess;
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

    // Cheese variables
    public boolean cheese = false;

    public int group0Count;
    public int group1Count;
    public boolean finishedPostCalc = false;

    public MilkInformation milkInformation;
    public CheesePostProcess cheeseStrat;
    public ChaseStrategyUtil chaseStrategy;

    public MapLocation center;
    public MapLocation bestCoverageLocation;

    private boolean largeMap = false;
    private boolean mediumMap = false;
    private int milkingSpot = 0;

    public Offensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new HQCom(this);
        largeMap = info.width * info.height > BehaviorConstants.MAP_LARGE_MINIMUM_AREA;
        mediumMap = !largeMap && info.width * info.height > BehaviorConstants.MAP_MEDIUM_MINIMUM_AREA;
        milkInformation = new MilkInformation(this);
        cheeseStrat = new CheesePostProcess(this);
        chaseStrategy = new ChaseStrategyUtil(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        // determines the base behavior to run
        group0Count = getCount(0);
        group1Count = getCount(1);

        // Gets the different groups
        boolean enough0Attack = group0Count >= BehaviorConstants.HQ_REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean enough1Attack = group1Count >= BehaviorConstants.HQ_REQUIRED_SOLDIER_COUNT_FOR_ATTACK;
        boolean combinedEnoughAttack = group0Count + group1Count >= BehaviorConstants.HQ_REQUIRED_SOLDIER_COUNT_FOR_GROUP_ATTACK;

        // TODO: cheese
        if (BehaviorConstants.CHEESE_ENABLED && cheeseStrat.cheese) {
            cheese = true;
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
            oneBase = !hunt0 && Clock.getRoundNum() > BehaviorConstants.HQ_SMALL_MAP_ONE_BASE_ROUND_NUMBER;
        }
        huddle = !hunt0 && !hunt1 && !chase0 && !chase1 && !oneBase;
    }

    @Override
    public void postProcessing() throws GameActionException {
        super.postProcessing();
        if (finishedPostCalc || hqPostProcessing) {
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

        cheeseStrat.calc();

        if (cheeseStrat.finished) {
            finishedPostCalc = true;
        }
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

    private void _calculateRallyPoint() throws GameActionException {
        int halfWidth = info.width / 2;
        center = new MapLocation(halfWidth, info.height / 2);
        MapLocation hq = info.hq;
        Direction dir = info.enemyDir;
        int[][] map = this.map.map;
        int width = info.width;
        int height = info.height;

        for (int i = 0; i < 8; i++) {
            boolean found = true;
            MapLocation curr = hq;
            for (int j = 0; j < 3; j++) {
                curr = curr.add(dir);
                if (curr.x < 0 || curr.x > width || curr.y < 0 || curr.y > height && map[curr.x][curr.y] == 1) {
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
            if (map[curr.x][curr.y] == 0) {
                bestCoverageLocation = curr;
                break;
            }
        }

        // TODO: Can there actual errors?
    }
}

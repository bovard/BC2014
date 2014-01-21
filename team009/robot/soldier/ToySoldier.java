package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.communication.bt.SoldierCom;
import team009.communication.decoders.GroupCommandDecoder;
import team009.robot.TeamRobot;
import team009.toyBT.ToySelector;
import team009.utils.SmartRobotInfoArray;

public class ToySoldier extends TeamRobot {


    public boolean seesEnemyTeamNonHQRobot = false;
    public boolean seesEnemyTeamNonHQBuilding = false;
    public boolean seesEnemySoldier = false;
    public boolean seesEnemyNoise = false;
    public boolean seesEnemyPastr = false;
    public boolean seesEnemyHQ = false;
    public boolean engagedInCombat = false;
    public SmartRobotInfoArray enemySoldiers;
    public SmartRobotInfoArray friendlySoldiers;
    public SmartRobotInfoArray enemyPastrs;
    public SmartRobotInfoArray enemyNoise;
    public GroupCommandDecoder groupCommand;
    public GroupCommandDecoder hqCommand;
    public Robot[] enemies = new Robot[0];
    public Robot[] allies = new Robot[0];
    public MapLocation currentLoc;
    public MapLocation lastLoc;
    public MapLocation comLocation;
    public int comCommand = 0;
    public double health;
    public GroupCommandDecoder decoder;
    public int sensorRadiusSquared = RobotType.SOLDIER.sensorRadiusSquared;


    // Permanent behaviors from coms.
    public boolean isHunter = true;
    public boolean isHerder = false;

    public ToySoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = new ToySelector(this);
        comRoot = new SoldierCom(this);
        comLocation = new MapLocation(0, 0);
    }

    @Override
    protected Node getTreeRoot() {
        return new ToySelector(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        MapLocation temp = rc.getLocation();
        if (!temp.equals(currentLoc)) {
            lastLoc = currentLoc;
        }
        currentLoc = temp;
        health = rc.getHealth();
        enemySoldiers = new SmartRobotInfoArray();
        enemyNoise = new SmartRobotInfoArray();
        enemyPastrs = new SmartRobotInfoArray();
        friendlySoldiers = new SmartRobotInfoArray();

        // micro stuff
        // TODO: get a better picture by sensing how many of our allies are soliders?
        // TODO: Morph this into one call (thats an extra 100 byte codes for no reason)
        Robot[] robots = rc.senseNearbyGameObjects(Robot.class, sensorRadiusSquared);
        for (Robot r : robots) {
            RobotInfo info = rc.senseRobotInfo(r);

            if (info.team == this.info.enemyTeam) {
                if (info.type == RobotType.HQ) {
                    seesEnemyHQ = true;
                } else if (info.type == RobotType.SOLDIER) {
                    enemySoldiers.add(info);
                } else if (info.type == RobotType.NOISETOWER) {
                    enemyNoise.add(info);
                } else if (info.type == RobotType.PASTR) {
                    enemyPastrs.add(info);
                }
            } else {
                if (info.type == RobotType.SOLDIER) {
                    friendlySoldiers.add(info);
                }
            }
        }

        seesEnemyHQ = false;
        seesEnemySoldier = enemySoldiers.length > 0;
        seesEnemyPastr = enemyPastrs.length > 0;
        seesEnemyNoise = enemyNoise.length > 0;
        seesEnemyTeamNonHQRobot = seesEnemySoldier || seesEnemyNoise || seesEnemyPastr;
        seesEnemyTeamNonHQBuilding = seesEnemyNoise || seesEnemyPastr;
        engagedInCombat = enemySoldiers.length > 0 && currentLoc.distanceSquaredTo(enemySoldiers.arr[0].location) < RobotType.SOLDIER.attackRadiusMaxSquared;

        // What type of toy soldier is this.
        // TODO: Bovard what to do?
        isHerder = comCommand == CAPTURE_PASTURE || comCommand == CAPTURE_SOUND;
        isHunter = !isHerder;
    }

    // TODO: WayPointing?
    public void postProcessing() throws GameActionException {}
}

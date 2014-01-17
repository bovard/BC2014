package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.communication.bt.SoldierCom;
import team009.combat.CombatUtils;
import team009.communication.GroupCommandDecoder;
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
    public SmartRobotInfoArray enemySoldiers = new SmartRobotInfoArray();
    public SmartRobotInfoArray friendlySoldiers = new SmartRobotInfoArray();
    public SmartRobotInfoArray enemyPastrs = new SmartRobotInfoArray();
    public SmartRobotInfoArray enemyNoise = new SmartRobotInfoArray();
    public GroupCommandDecoder groupCommand;
    public GroupCommandDecoder hqCommand;
    public Robot[] enemies = new Robot[0];
    public Robot[] allies = new Robot[0];
    public RobotInfo[] enemyRobotInfo = new RobotInfo[0];
    public int group;
    public int type;
    public MapLocation currentLoc;
    public MapLocation lastLoc;
    public double health;
    public GroupCommandDecoder decoder;

    public ToySoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = new ToySelector(this);
        comRoot = new SoldierCom(this);
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
        enemySoldiers.length = 0;
        enemyNoise.length = 0;
        enemyPastrs.length = 0;
        friendlySoldiers.length = 0;

        // micro stuff
        // TODO: get a better picture by sensing how many of our allies are soliders?
        // TODO: Morph this into one call (thats an extra 100 byte codes for no reason)
        Robot[] robots = rc.senseNearbyGameObjects(Robot.class, 100);
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
    }

    //TODO: $DEBUG$
    public void postProcessing() throws GameActionException {
//        rc.setIndicatorString(0, "Com from group: " + groupCommand + (groupCommand != null ? groupCommand.toString() : ""));
//        rc.setIndicatorString(1, "Com from HQ: " + hqCommand + (hqCommand != null ? hqCommand.toString() : ""));
    }

    // TODO: $Efficiency$ do something different than funcions :)
    public boolean hasAttackSignal() {
        return groupCommand != null && groupCommand.command == ATTACK;
    }

    public boolean hasPastrAttackSignal() {
        return hqCommand != null && hqCommand.command == ATTACK_PASTURE;
    }

    public boolean hasDefendPastrSignal() {
        return hqCommand != null && hqCommand.command == DEFEND;
    }

    public boolean hasReturnToBaseSignal() {
        return hqCommand != null && hqCommand.command == RETURN_TO_BASE;
    }

    public MapLocation getHQCommandLocation() {
        return hqCommand != null ? hqCommand.location : null;
    }
}

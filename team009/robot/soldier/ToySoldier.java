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
    public SmartRobotInfoArray enemySoldiers = new SmartRobotInfoArray();
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

        // micro stuff
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        // TODO: get a better picture by sensing how many of our allies are soliders?
        allies = rc.senseNearbyGameObjects(Robot.class, 100, info.myTeam);
        enemyRobotInfo = CombatUtils.getRobotInfo(enemies, rc);
        enemySoldiers.length = 0;
        enemyNoise.length = 0;
        enemyPastrs.length = 0;
        seesEnemyHQ = false;
        for ( RobotInfo r : enemyRobotInfo) {
            if (r.type == RobotType.HQ) {
                seesEnemyHQ = true;
            } else if (r.type == RobotType.SOLDIER) {
                enemySoldiers.add(r);
            } else if (r.type == RobotType.NOISETOWER) {
                enemyNoise.add(r);
            } else if (r.type == RobotType.PASTR) {
                enemyPastrs.add(r);
            }
        }
        seesEnemySoldier = enemySoldiers.length > 0;
        seesEnemyPastr = enemyPastrs.length > 0;
        seesEnemyNoise = enemyNoise.length > 0;
        seesEnemyTeamNonHQRobot = seesEnemySoldier || seesEnemyNoise || seesEnemyPastr;
        seesEnemyTeamNonHQBuilding = seesEnemyNoise || seesEnemyPastr;
    }

    //TODO: $DEBUG$
    public void postProcessing() throws GameActionException {
//        rc.setIndicatorString(0, "Com from group: " + groupCommand + (groupCommand != null ? groupCommand.toString() : ""));
//        rc.setIndicatorString(1, "Com from HQ: " + hqCommand + (hqCommand != null ? hqCommand.toString() : ""));
    }

    public boolean hasAttackSignal() {
        return groupCommand != null && groupCommand.command == ATTACK;
    }

    public boolean hasPastrAttackSignal() {
        return hqCommand != null && hqCommand.command == ATTACK_PASTURE;
    }

    public boolean hasDefendPastrSignal() {
        return hqCommand != null && hqCommand.command == DEFEND;
    }

    public MapLocation getHQCommandLocation() {
        return hqCommand != null ? hqCommand.location : null;
    }
}

package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
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


        // TODO: Michael remove this when you have put it elsewhere
        /*
        // writes out any information about its environment.
        if (Communicator.WriteRound(round)) {
            Communicator.WriteTypeAndGroup(rc, type, group);

            // If there is no decoder or no data, then write out information about the environment.
            if (seesEnemy && (decoder == null || !decoder.hasData() || decoder.command == BaseSoldier.DEFEND)) {
                Communicator.WriteToGroup(rc, group, BaseSoldier.ATTACK, firstNonHQEnemy.location);
            }
        }

        // Updates the decoder with any information.
        if (Communicator.ReadRound(round)) {
            decoder = Communicator.ReadFromGroup(rc, group);
        }
        */
    }
}

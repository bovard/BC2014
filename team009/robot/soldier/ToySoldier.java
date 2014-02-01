package team009.robot.soldier;

import battlecode.common.*;
import team009.RobotInformation;
import team009.bt.Node;
import team009.communication.bt.SoldierCom;
import team009.communication.decoders.GroupCommandDecoder;
import team009.robot.TeamRobot;
import team009.toyBT.ToySelector;
import team009.utils.HQAttackUtil;
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
      public SmartRobotInfoArray enemySoldiersInRange;
      public int enemySoldiersInCombatRange = 0;
      public SmartRobotInfoArray friendlySoldiers;
      public SmartRobotInfoArray friendlyPastrs;
      public SmartRobotInfoArray enemyPastrs;
      public SmartRobotInfoArray enemyNoise;
      public RobotInfo enemyHqInfo;
      public GroupCommandDecoder groupCommand;
      public GroupCommandDecoder hqCommand;
      public Robot[] enemies = new Robot[0];
      public int nearestAllyDistance = 100;
      public RobotInfo nearestAlly = null;
      public int nearestEnemyDistance = 100;
      public RobotInfo nearestEnemy = null;
      public MapLocation currentLoc;
      public MapLocation lastLoc;
      public MapLocation comLocation;
      public int comCommand = 0;
      public double health;
      public GroupCommandDecoder decoder;
      public int sensorRadiusSquared = RobotType.SOLDIER.sensorRadiusSquared;
      public int myGroupCount = 0;
      public HQAttackUtil hqAttack;

      public boolean requestLocation = false;
      public boolean locationRequested = false;
      public MapLocation start = null;
      public MapLocation end = null;
      public MapLocation locationResult = null;


      // Permanent behaviors from coms.
      public boolean isHunter = true;
      public boolean isHerder = false;

      public ToySoldier(RobotController rc, RobotInformation info) {
          super(rc, info);
          hqAttack = new HQAttackUtil(this);
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
          enemySoldiersInRange = new SmartRobotInfoArray();
          enemyNoise = new SmartRobotInfoArray();
          enemyPastrs = new SmartRobotInfoArray();
          friendlySoldiers = new SmartRobotInfoArray();
          friendlyPastrs = new SmartRobotInfoArray();

          // micro stuff
          // TODO: get a better picture by sensing how many of our allies are soliders?
          // TODO: Morph this into one call (thats an extra 100 byte codes for no reason)
          nearestAllyDistance = 100;
          nearestAlly = null;
          nearestEnemyDistance = 100;
          nearestEnemy = null;
          enemySoldiersInCombatRange = 0;
          Robot[] robots = rc.senseNearbyGameObjects(Robot.class, sensorRadiusSquared);
          for (Robot r : robots) {
              RobotInfo info = rc.senseRobotInfo(r);

              if (info.team == this.info.enemyTeam) {
                  if (info.type == RobotType.HQ) {
                      seesEnemyHQ = true;
                      enemyHqInfo = info;
                  } else if (info.type == RobotType.SOLDIER) {
                      enemySoldiers.add(info);
                      int dist = info.location.distanceSquaredTo(currentLoc);
                      if (dist < RobotType.SOLDIER.attackRadiusMaxSquared) {
                          enemySoldiersInCombatRange++;
                          enemySoldiersInRange.add(info);
                      }
                      if (dist < nearestEnemyDistance) {
                          nearestEnemyDistance = dist;
                          nearestEnemy = info;
                      }

                  } else if (info.type == RobotType.NOISETOWER) {
                      enemyNoise.add(info);
                  } else if (info.type == RobotType.PASTR) {
                      enemyPastrs.add(info);
                  }
              } else {
                  if (info.type == RobotType.SOLDIER) {
                      friendlySoldiers.add(info);
                      int dist = info.location.distanceSquaredTo(currentLoc);
                      if (dist < RobotType.SOLDIER.attackRadiusMaxSquared) {
                          enemySoldiersInCombatRange++;
                      }
                      if (dist < nearestEnemyDistance) {
                          nearestEnemyDistance = dist;
                          nearestEnemy = info;
                      }
                  } else if (info.type == RobotType.PASTR) {
                      friendlyPastrs.add(info);
                  }
              }
          }
          engagedInCombat = enemySoldiersInCombatRange > 0;

          seesEnemyHQ = false;
          seesEnemySoldier = enemySoldiers.length > 0;
          seesEnemyPastr = enemyPastrs.length > 0;
          seesEnemyNoise = enemyNoise.length > 0;
          seesEnemyTeamNonHQRobot = seesEnemySoldier || seesEnemyNoise || seesEnemyPastr;
          seesEnemyTeamNonHQBuilding = seesEnemyNoise || seesEnemyPastr;


          // What type of toy soldier is this.
          // TODO: Bovard what to do?
          isHerder = comCommand == CAPTURE_PASTURE || comCommand == CAPTURE_SOUND;
          isHunter = !isHerder;

          // Checks to see if we can sense hq.
          if (seesEnemyHQ) {
              rc.setIndicatorString(2, "setting action delay: " + enemyHqInfo.actionDelay + " : at Round: " + round);
              hqAttack.setActionDelay(enemyHqInfo.actionDelay);
          }
      }

      public void suicide(int enemiesHit) throws GameActionException {
          // TODO: when we suicide, if we hit more than 2 people add to the suicide com channel
          // if we only hit 2 do nothing
          // if we only hit 1 subtract 1
          // if we hit zero subtract 2

          // this will be used to see if the enemy has anti-suicide micro
          // aka are our suicides working!
          // (we only consider suicide if the value > 0, starts at 5ish?)
          rc.selfDestruct();
      }

      // TODO: WayPointing?
      public void postProcessing() throws GameActionException {}
  }

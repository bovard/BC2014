package team009.robot.hq;

import battlecode.common.*;
import team009.MapUtils;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.hq.HQShoot;
import team009.communication.Communicator;
import team009.communication.SoldierCountDecoder;
import team009.robot.TeamRobot;
import team009.robot.soldier.SoldierSpawner;

public abstract class HQ extends TeamRobot {

    // TODO: get rid of this once they patch
    private Node shoot = new HQShoot(this);

    private int maxSoldiers;
    SoldierCountDecoder[] soldierCounts;
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
        maxSoldiers = SoldierSpawner.SOLDIER_COUNT * SoldierSpawner.MAX_GROUP_COUNT;
        soldierCounts = new SoldierCountDecoder[maxSoldiers];
        // REMEMBER TO CALL treeRoot = getTreeRoot() in your implementations of this!
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        RobotInfo firstNonHQEnemy = null;
        enemies = rc.senseNearbyGameObjects(Robot.class, 100, info.enemyTeam);
        seesEnemy = false;
        if (enemies.length > 0) {
            seesEnemy = true;
            firstNonHQEnemy = getFirstNonHQRobot(enemies);
            if (firstNonHQEnemy == null) {
                seesEnemy = false;
                enemies = new Robot[0];
            }
        }

        if (Communicator.ReadRound(round)) {
            int groupCount = SoldierSpawner.MAX_GROUP_COUNT;

            // TODO: $DEBUG$
            String soldierString = "";
            for (int i = 0; i < maxSoldiers; i++) {

                int group = i % groupCount;
                int type = i / groupCount;
                soldierCounts[i] = Communicator.ReadTypeAndGroup(rc, type, group);
                Communicator.ClearCountChannel(rc, type, group);

                if (group == 0) {
                    soldierString += "Type: " + soldierCounts[i].soldierType + " : Count: " + soldierCounts[i].count + " ";
                }
            }

            rc.setIndicatorString(1, soldierString);
        }
    }

    // TODO: get rid of this once they patch
    @Override
    public void run() {
        while (true) {
            int round = Clock.getRoundNum();

            try {
                // at the start of the round, update with an environment check


                this.environmentCheck();



                // have the tree choose what to do
                if (rc.isActive()) {
                    treeRoot.run();
                }

                // TODO: Had to add this here since the HQ can shoot while not active!
                if (shoot.pre()) {
                    shoot.run();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            // then postProcessing with whatever remains of our byte code
            try {
                this.postProcessing();
            } catch (Exception e) {
                System.out.println("Load error: " );
                e.printStackTrace();
            }

            // only yield if we're still on the same clock turn
            // if we aren't that means that we ended up skipping
            // our turn because we went too long
            if (round == Clock.getRoundNum()) {
                this.rc.yield();
            } else {
                System.out.println("BYTECODE LIMIT EXCEEDED!");
            }
        }
    }

public void createDumbSoldier(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_DUMB, group);
    }

    public void createWolf(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_WOLF, group);
    }

    // TODO: $IMPROVEMENT$ We should make the group number have a channel to grab pasture location from
    public void createHerder(int group, MapLocation pasture) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_HERDER, group, pasture);
    }

    public void createSoundTower(int group, MapLocation towerLocation) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_SOUND_TOWER, group, towerLocation);
    }

    public void createDumbPastrHunter() throws GameActionException {
        _spawn(SoldierSpawner.DUMB_PASTR_HUNTER, 0);
    }

    public void createPastureCapturer(int group, MapLocation pasture) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_PASTURE_CAPTURER, group, pasture);
    }

    public void createBackDoorNoisePlanter(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_BACKDOOR_NOISE_PLANTER, group);
    }

    public void createJackal(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_JACKAL, group);
    }

    private void _spawn(int soldierType, int group) throws GameActionException {
        Direction dir = _getSpawnDirection();
        rc.spawn(dir);
        Communicator.WriteNewSoldier(rc, soldierType, group, new MapLocation(1, 1));
    }

    private void _spawn(int soldierType, int group, MapLocation location) throws GameActionException {
        Direction dir = _getSpawnDirection();
        if (dir == null) {
            return;
        }
        rc.spawn(dir);
        Communicator.WriteNewSoldier(rc, soldierType, group, location);
    }

    private Direction _getSpawnDirection() {
        Direction dir = null;
        boolean done = false;
        int tries = 0;
        while(!done && tries < 20) {
            tries++;
            dir = MapUtils.getRandomDir();
            if (rc.canMove(dir)) {
                done = true;
            }
        }

        return done ? dir : null;
    }

    //TODO move this method to the base class since its shared by HQ and BaseSolider
    private RobotInfo getFirstNonHQRobot(Robot[] robots) throws GameActionException {
        for (Robot r : robots) {
            RobotInfo info = rc.senseRobotInfo(r);

            if (info.type != RobotType.HQ) {
                return info;
            }
        }

        return null;
    }

    public Direction getRandomSpawnDirection() {
        return _getSpawnDirection();
    }


}

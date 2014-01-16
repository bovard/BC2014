package team009.robot.hq;

import battlecode.common.*;
import team009.MapUtils;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.hq.HQShoot;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.communication.SoldierCountDecoder;
import team009.communication.bt.HQCom;
import team009.robot.TeamRobot;
import team009.robot.soldier.SoldierSpawner;

public abstract class HQ extends TeamRobot {

    // TODO: get rid of this once they patch
    private Node shoot = new HQShoot(this);
    public int maxSoldiers;
    public SoldierCountDecoder[] soldierCounts;
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
        maxSoldiers = SoldierSpawner.SOLDIER_COUNT * Communicator.MAX_GROUP_COUNT;
        soldierCounts = new SoldierCountDecoder[maxSoldiers];
        // REMEMBER TO CALL treeRoot = getTreeRoot() in your implementations of this!
        comRoot = new HQCom(this);
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
    }

    // TODO: BUG IN CODE it seems to be missing 1
    public int getCount(int type, int group) {
        int idx = type * Communicator.MAX_GROUP_COUNT + group;
        return soldierCounts[idx] == null ? 0 : soldierCounts[idx].count + 1;
    }

    public void comReturnHome(MapLocation loc, int group) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, RETURN_TO_BASE, true)) {
            Communicator.WriteToGroup(rc, group, RETURN_TO_BASE, Communicator.GROUP_HQ_CHANNEL, loc, 1000);
        }
    }

    public void comAttackPasture(MapLocation loc, int group) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, ATTACK_PASTURE, true) && !loc.equals(dec.location)) {
            Communicator.WriteToGroup(rc, group, ATTACK_PASTURE, Communicator.GROUP_HQ_CHANNEL, loc, 1000);
        }
    }

    public boolean comDefend(MapLocation loc, int group) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, DEFEND, true) && !loc.equals(dec.location)) {
            Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, DEFEND, loc, 1000);
            return true;
        }

        return false;
    }

    /**
     * Clears the coms if the location in the coms are not the same as the location provided.
     * @throws GameActionException
     */
    public boolean comClear(int group, MapLocation hasLocation) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (dec == null || !dec.hasData()) {
            return false;
        }
        if (hasLocation.equals(dec.location)) {
            return false;
        }
        Communicator.ClearGroupChannel(rc, group, Communicator.GROUP_HQ_CHANNEL);
        return true;
    }

    public void createToySoldier(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_TOY_SOLDIER, group);
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

    public void createDefender(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_DEFENDER, group);
    }

    private void _spawn(int soldierType, int group) throws GameActionException {
        Direction dir = _getSpawnDirection();
        if (dir == null) {
            return;
        }
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

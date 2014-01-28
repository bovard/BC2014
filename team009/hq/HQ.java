package team009.hq;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.MapUtils;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.decoders.GroupCommandDecoder;
import team009.communication.decoders.SoldierCountDecoder;
import team009.robot.TeamRobot;
import team009.robot.soldier.SoldierSpawner;
import team009.utils.MapPreProcessor;
import team009.utils.SmartMapLocationArray;

public abstract class HQ extends TeamRobot {
    private int twoWayComPosition;

    public boolean hqPostProcessing = true;
    public int maxSoldiers;
    public SoldierCountDecoder soldierCounts;
    public SoldierCountDecoder pastrCounts;
    public SoldierCountDecoder noiseCounts;
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];
    public boolean hasPastures = false;
    public boolean weHavePastures = false;
    public boolean hasHQPastures = false;
    public SmartMapLocationArray enemyPastrs;
    public SmartMapLocationArray pastrLocations = new SmartMapLocationArray();
    public SmartMapLocationArray noiseLocations = new SmartMapLocationArray();
    public MapPreProcessor map;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
        maxSoldiers = Communicator.MAX_GROUP_COUNT;
        map = new MapPreProcessor(this);
        twoWayComPosition = 0;

        // REMEMBER TO CALL treeRoot = getTreeRoot() in your implementations of this!
        // REMEMBER TO CALL comRoot = __YOUR_COM_ROOT__; // See communications.bt.HQCom for example (WriteCom and ReadCom)
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();
        RobotInfo firstNonHQEnemy;
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

        MapLocation[] pastrs = rc.sensePastrLocations(info.enemyTeam);
        MapLocation[] homePastrs = rc.sensePastrLocations(info.myTeam);
        weHavePastures = homePastrs.length > 0;

        enemyPastrs = new SmartMapLocationArray();
        for (int i = 0, j = 0; i < 2 && j < pastrs.length; j++) {
            if (pastrs[i].isAdjacentTo(info.enemyHq)) {
                hasHQPastures = true;
            } else {
                enemyPastrs.add(pastrs[i]);
            }
        }

        hasPastures = enemyPastrs.length > 0;
    }

    @Override
    public void postProcessing() throws GameActionException {
        if (!hqPostProcessing) {
            return;
        }

        // Calculate until all is finsihed.
        hqPostProcessing = !map.finished;
    }

    public int getCount(int group) {
        if (group == 0) {
            return soldierCounts == null ? 0 : soldierCounts.count;
        } else if (group == TeamRobot.PASTR_GROUP) {
            return pastrCounts == null ? 0 : pastrCounts.count;
        } else if (group == TeamRobot.NOISE_TOWER_GROUP) {
            return noiseCounts == null ? 0 : noiseCounts.count;
        }
        return 0;
    }

    // make this a more optimal spot
    public void comReturnHome(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, RETURN_TO_BASE, loc, 200);
    }

    public void comAttackPasture(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, ATTACK_PASTURE, loc, 200);
    }

    public void comDefend(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, DEFEND, loc, 200);
    }

    public void comCapture(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, CAPTURE_PASTURE, loc, 200);
    }

    public void comHQSurround(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, HQ_SURROUND, loc, 200);
    }

    public void comSoundTower(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, CAPTURE_SOUND, loc, 200);
    }

    public boolean comDestruct(int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, DESTRUCT, new MapLocation(0, 0), getCount(group));
        return true ;
    }

    public boolean comDefend(MapLocation loc, int group, int ttl) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, DEFEND, true) && !loc.equals(dec.location)) {
            Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, DEFEND, loc, ttl);
            return true;
        }

        return false;
    }
    // TODO: $IMPROVEMENT$ We should make the group number have a channel to grab pasture location from
    public void createHerder(int group, MapLocation pasture) throws GameActionException {
    }

    public void createSoundTower(int group, MapLocation towerLocation) throws GameActionException {
    }

    public void createNoiseTower(int group, MapLocation towerLocation) throws GameActionException {
    }

    public void createDumbPastrHunter() throws GameActionException {
    }

    public void createPastureCapturer(int group, MapLocation pasture) throws GameActionException {
    }

    public void createBackDoorNoisePlanter(int group) throws GameActionException {
    }

    public void createJackal(int group) throws GameActionException {
    }

    public void createDefender(int group) throws GameActionException {
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
        _spawn(SOLDIER_TYPE_TOY_SOLDIER, group, null);
    }

    private void _spawn(int soldierType, int group, MapLocation location) throws GameActionException {
        Direction dir = _getSpawnDirection();
        if (dir == null) {
            return;
        }
        rc.spawn(dir);

        Communicator.WriteNewSoldier(rc, soldierType, group, Communicator.TWO_WAY_HQ_COM_BASE + twoWayComPosition, location);
        twoWayComPosition = (twoWayComPosition + 1) % Communicator.TWO_WAY_COM_LENGTH;
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

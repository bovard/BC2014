package team009.robot.hq;

import battlecode.common.*;
import team009.MapUtils;
import team009.RobotInformation;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.communication.SoldierCountDecoder;
import team009.communication.bt.HQCom;
import team009.robot.TeamRobot;
import team009.robot.soldier.SoldierSpawner;

public abstract class HQ extends TeamRobot {

    public int maxSoldiers;
    public SoldierCountDecoder[] soldierCounts;
    public boolean seesEnemy = false;
    public Robot[] enemies = new Robot[0];
    public MapLocation bestLocation;
    public boolean hasPastures = false;
    public MapLocation[] pastures = new MapLocation[0];

    public MapLocation bestRegenLoc = null;
    public boolean foundBest = false;
    private double[][] cowRegens;
    private double bestRegen;
    private int regenRow = 1;
    private int regenColumn = 1;
    private int rowLen = 0;
    private int colLen = 0;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
        maxSoldiers = Communicator.MAX_GROUP_COUNT;
        soldierCounts = new SoldierCountDecoder[maxSoldiers];
        // REMEMBER TO CALL treeRoot = getTreeRoot() in your implementations of this!
        comRoot = new HQCom(this);
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
        if (pastrs.length > 1) {
            pastures = new MapLocation[2];
            pastures[0] = pastrs[0];
            pastures[1] = pastrs[1];
            hasPastures = true;
        } else if (pastrs.length == 1) {
            pastures = new MapLocation[1];
            pastures[0] = pastrs[0];
            hasPastures = true;
        } else {
            hasPastures = false;
        }
    }

    /**
     * Post processes best pasture locations
     */
    public void postProcessing() throws GameActionException {
        if (foundBest) {
            return;
        }

        _calculateBestCowPosition();
        foundBest = regenRow == rowLen;
        if (foundBest) {
            MapLocation mirror = new MapLocation(bestRegenLoc.y, bestRegenLoc.x);
            if (mirror.distanceSquaredTo(info.hq) < bestRegenLoc.distanceSquaredTo(info.hq)) {
                bestRegenLoc = mirror;
            }
        }
    }

    private void _calculateBestCowPosition() {
        if (cowRegens == null) {
            cowRegens = rc.senseCowGrowth();
            bestRegen = cowRegens[0][0];
            bestRegenLoc = new MapLocation(0, 0);
            rowLen = cowRegens.length - 2;
            colLen = cowRegens[0].length - 2;
        }
        double[][] cowRegens = this.cowRegens;

        // TODO: Better would be to check all neighbors, but that is expensive.
        // TODO: So best regeneration rate is you + your neighbors
        // TODO: But even that may not be the best.  Then comes distance to travel to, etc etc
        int roundsToProcess = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum())) / 55;
        double bestRegen = this.bestRegen;
        int regenRow = this.regenRow;
        int regenColumn = this.regenColumn;
        int i = 0;
        while (regenRow < rowLen && i < roundsToProcess) {

            // One row at a time
            for (; regenColumn < colLen && i < roundsToProcess; regenColumn++, i++) {
                double[] row = cowRegens[regenRow];
                double val = row[regenColumn - 1] + row[regenColumn] + row[regenColumn + 1] + cowRegens[regenRow - 1][regenColumn] + cowRegens[regenRow + 1][regenColumn];
                if (bestRegen < val) {
                    bestRegen = val;
                    bestRegenLoc = new MapLocation(regenRow, regenColumn);
                }
            }

            if (regenColumn == colLen) {
                regenColumn = 1;
                regenRow++;
            }
        }

        this.bestRegen = bestRegen;
        this.regenRow = regenRow;
        this.regenColumn = regenColumn;
    }

    public int getCount(int group) {
        return soldierCounts[group] == null ? 0 : soldierCounts[group].count;
    }

    // make this a more optimal spot
    public void comReturnHome(MapLocation loc, int group) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, RETURN_TO_BASE, true)) {
            Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, RETURN_TO_BASE, loc, 200);
        }
    }

    public void comAttackPasture(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, ATTACK_PASTURE, loc, 200);
    }

    public void comDefend(MapLocation loc, int group) throws GameActionException {
        Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, DEFEND, loc, 200);
    }

    public boolean comCapture(MapLocation loc, int group) throws GameActionException {
        GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, group, Communicator.GROUP_HQ_CHANNEL);
        if (GroupCommandDecoder.shouldCommunicate(dec, loc, CAPTURE_PASTURE, true) && !loc.equals(dec.location)) {
            Communicator.WriteToGroup(rc, group, Communicator.GROUP_HQ_CHANNEL, CAPTURE_PASTURE, loc, 200);
            return true;
        }

        return false;
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

package team009.robot;

import battlecode.common.*;
import team009.MapUtils;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.HQSelector;
import team009.communication.Communicator;
import team009.communication.SoldierCountDecoder;
import team009.robot.soldier.SoldierSpawner;

public class HQ extends TeamRobot {

    // This will adjust to how many soldiers we have.
    private int maxSoldiers;
    SoldierCountDecoder[] soldierCounts;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);

        maxSoldiers = SoldierSpawner.SOLDIER_COUNT * SoldierSpawner.MAX_GROUP_COUNT;
        soldierCounts = new SoldierCountDecoder[maxSoldiers];
    }

    @Override
    protected Node getTreeRoot() {
        return new HQSelector(this);
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

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

    public void createDumbSoldier(int group) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_DUMB, group);
    }

    // TODO: $IMPROVEMENT$ We should make the group number have a channel to grab pasture location from
    public void createHerder(int group, MapLocation pasture) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_HERDER, group, pasture);
    }

    public void createPastureCapturer(int group, MapLocation pasture) throws GameActionException {
        _spawn(SoldierSpawner.SOLDIER_TYPE_PASTURE_CAPTURER, group, pasture);
    }

    private void _spawn(int soldierType, int group) throws GameActionException {
        Direction dir = _getSpawnDirection();
        rc.spawn(dir);
        Communicator.WriteNewSoldier(rc, soldierType, new MapLocation(1, 1));
    }

    private void _spawn(int soldierType, int group, MapLocation location) throws GameActionException {
        Direction dir = _getSpawnDirection();
        rc.spawn(dir);
        Communicator.WriteNewSoldier(rc, soldierType, location);
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
}

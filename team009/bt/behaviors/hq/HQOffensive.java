package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class HQOffensive extends Behavior {
    private HQ hq;
    private boolean proximityTowers;

    public HQOffensive(HQ robot) {
        super(robot);
        hq = robot;
        proximityTowers = false;
    }

    @Override
    public boolean pre() throws GameActionException {
        // no preconditions
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {

        //robot count
        int robotCount = robot.rc.senseRobotCount();

        if(robotCount > 4 && !proximityTowers) {
            //spawn a proximity tower to gather cows
            MapLocation proxTower = hq.currentLoc.add(hq.getRandomSpawnDirection(), 1);
            hq.createSoundTower(0, proxTower);
            proximityTowers = true;
            return true;
        }

        // spawn guys
        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            hq.createDefender(TeamRobot.DEFENDER_GROUP);
            return true;
        }

        return true;
    }

}

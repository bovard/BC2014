package team009.bt.behaviors.hq;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.hq.HQ;

public class HQSprint extends Behavior {
    private boolean proximityTowers;
    private boolean backDoor;

    public HQSprint(HQ robot) {
        super(robot);
        proximityTowers = false;
        backDoor = false;
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {

        //robot count
        int robotCount = robot.rc.senseRobotCount();

        if(!backDoor) {
            ((HQ)robot).createBackDoorNoisePlanter(0);
            backDoor = true;
            return true;
        }

        if(robotCount > 4 && !proximityTowers) {
            //spawn a proximity tower to gather cows
            MapLocation proxTower = robot.currentLoc.add(((HQ)robot).getRandomSpawnDirection(), 1);
            ((HQ)robot).createSoundTower(0, proxTower);
            proximityTowers = true;
            return true;
        }

        // spawn guys
        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            ((HQ)robot).createWolf(0);
            return true;
        }
        return false;
    }
}

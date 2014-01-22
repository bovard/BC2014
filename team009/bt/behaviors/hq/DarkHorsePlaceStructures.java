package team009.bt.behaviors.hq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.MapUtils;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.DarkHorse;
import team009.robot.hq.HQ;

public class DarkHorsePlaceStructures extends Behavior{
    private Direction toSpawnPastr;
    private Direction toSpawnNoise;

    public DarkHorsePlaceStructures(TeamRobot robot) {
        super(robot);
        toSpawnPastr = robot.info.enemyHq.directionTo(robot.info.hq);

        while(!robot.rc.canMove(toSpawnPastr)) {
            toSpawnPastr = toSpawnPastr.rotateLeft();
        }
        toSpawnNoise = toSpawnPastr.rotateLeft();
        while(!robot.rc.canMove(toSpawnNoise)) {
            toSpawnNoise = toSpawnNoise.rotateLeft();
        }
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((DarkHorse)robot).robotCount < 2;
    }

    @Override
    public boolean run() throws GameActionException {
        if (toSpawnNoise != null) {
            ((HQ)robot).createNoiseTower(0, robot.info.hq.add(toSpawnNoise));
            toSpawnNoise = null;
            return true;
        }
        if (toSpawnPastr != null) {
            ((HQ)robot).createPastureCapturer(0, robot.info.hq.add(toSpawnPastr));
            toSpawnPastr = null;
            return true;
        }
        return false;
    }
}

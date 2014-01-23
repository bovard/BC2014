package team009.bt.behaviors.noise;

import battlecode.common.*;
import team009.bt.behaviors.Behavior;
import team009.robot.NoiseTower;
import team009.utils.CheesePostProcess;
import team009.utils.MapPreProcessor;

public class SoundTowerBehavior extends Behavior {
    NoiseTower tower;
    private int radius;
    private int[] radii;
    private Direction currentDir;

    public SoundTowerBehavior(NoiseTower robot) {
        super(robot);
        tower = robot;
        radii = CheesePostProcess.getBestDistances(robot.rc, robot.currentLoc);
        currentDir = Direction.NORTH;
        radius = radii[MapPreProcessor.DirectionToInt(currentDir)];
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
    }

    @Override
    public boolean run() throws GameActionException {
        robot.rc.attackSquare(pullInCardinalDirections());
        return true;
    }

    public MapLocation pullInCardinalDirections()
    {
        int i = 0;
        while (radius <= 3 && i < 3) {
            currentDir = currentDir.rotateRight();
            radius = radii[MapPreProcessor.DirectionToInt(currentDir)];
            i++;
        }
        MapLocation loc = robot.currentLoc.add(currentDir, radius);
        radius--;

        return loc;
    }
}


package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.robot.TeamRobot;

public class NoiseTowerBehavior extends Behavior {
    TeamRobot nt;
    public NoiseTowerBehavior(TeamRobot robot) {
        super(robot);
        nt = robot;
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
        //spin around in a cirle shooting the gun
        int x = ((int)(Math.random()*nt.info.width));
        int y = ((int)(Math.random()*nt.info.height));
        MapLocation randLoc = new MapLocation(x,y);
        nt.rc.attackSquareLight(randLoc);
        return true;
    }
}


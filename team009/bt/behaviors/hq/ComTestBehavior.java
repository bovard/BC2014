package team009.bt.behaviors.hq;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;
import team009.robot.hq.HQ;

public class ComTestBehavior extends Behavior {
    private int backDoorCount = 0;
    private HQ hq;
    private MapLocation[] locs = new MapLocation[4];
    private int lastCommand = -1000;
    private int idx = 0;

    public ComTestBehavior(HQ robot) {
        super(robot);
        hq = robot;
        locs[0] = new MapLocation(5, 5);
        locs[1] = new MapLocation(robot.info.width - 6, 5);
        locs[2] = new MapLocation(robot.info.width - 6, robot.info.height - 6);
        locs[3] = new MapLocation(5, robot.info.height - 6);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        int robotCount = robot.rc.senseRobotCount();

        if (robot.rc.isActive() && robotCount < GameConstants.MAX_ROBOTS) {
            hq.createToySoldier(0);
            return true;
        }

        if (Clock.getRoundNum() - lastCommand > 250) {
            lastCommand = Clock.getRoundNum();
            hq.comDefend(locs[idx], 0);
            idx = (idx + 1) % 4;
        }

        return true;
    }
}


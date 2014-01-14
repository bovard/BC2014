package team009.bt.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;
import team009.utils.MapQuadrantUtils;

/**
  * This should be used to escape the HQ, we'll moved blindly MAX times away from it
   */
public class EscapeHQ extends Behavior {
    private static final int MAX = 4;
    private int moved = 0;
    private MapLocation corner;
    private BugMove move;


    public EscapeHQ(TeamRobot robot) {
        super(robot);
        MapQuadrantUtils.hq = robot.info.hq;
        MapQuadrantUtils.enemyHq = robot.info.enemyHq;
        MapQuadrantUtils.height = robot.info.height;
        MapQuadrantUtils.width = robot.info.width;
        corner = MapQuadrantUtils.getMapCornerForQuadrant(
                         MapQuadrantUtils.getMapQuadrant(
                                 robot.info.hq.x, robot.info.hq.y));
        move = new BugMove(robot);
        move.setDestination(corner);
    }

    @Override
    public boolean pre() throws GameActionException {
        return moved < MAX;
    }

    @Override
    public boolean run() throws GameActionException {
        move.move();
        moved++;
        return true;
    }
 }

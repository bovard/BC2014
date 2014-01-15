package team009.bt.behaviors.soldier;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;
import team009.utils.MapQuadrantUtils;

public class CongragateInHQCorner extends Behavior {
    private MapLocation corner;
    private BugMove move;
    public CongragateInHQCorner(TeamRobot robot) {
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
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        move.move();
        return true;
    }
}

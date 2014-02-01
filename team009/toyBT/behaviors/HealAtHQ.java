package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;

public class HealAtHQ extends Behavior {
    private BugMove move;

    public HealAtHQ(TeamRobot robot) {
        super(robot);
        move = new BugMove(robot);
        move.setDestination(robot.info.hq.add(robot.info.enemyDir.opposite(), 4));
    }

    @Override
    public boolean pre() throws GameActionException {
        return robot.rc.getHealth() < 30;
    }

    @Override
    public boolean run() throws GameActionException {
        move.move();
        return true;
    }
}

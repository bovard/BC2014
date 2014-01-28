package team009.hq.bt.behaviors;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.TeamRobot;

public class HQTest extends Behavior {
    public HQTest(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean run() throws GameActionException {
        System.out.println("I'm active!");
        System.out.println("My action delay is now " + robot.rc.getActionDelay());
        //System.out.println("I'm spawning!");
        //((HQ)robot).createDumbPastrHunter();
        System.out.println("My action delay is now " + robot.rc.getActionDelay());
        System.out.println("I'm shooting!");
        robot.rc.attackSquare(robot.currentLoc);
        return true;
    }
}

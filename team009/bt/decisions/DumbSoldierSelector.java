package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.robot.TeamRobot;

public class DumbSoldierSelector extends Decision {

    public DumbSoldierSelector(TeamRobot robot) {
        super(robot);
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
        for (Node current : children) {
            if (current.pre()) {
                if (current.post()) {
                    current.reset();
                }
                return current.run();
            }
        }
    }
}

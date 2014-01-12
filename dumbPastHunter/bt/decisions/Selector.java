package dumbPastHunter.bt.decisions;

import battlecode.common.GameActionException;
import dumbPastHunter.bt.Node;
import dumbPastHunter.robot.TeamRobot;

public abstract class Selector extends Decision {

    public Selector(TeamRobot robot) {
        super(robot);
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

        return true;
    }
}

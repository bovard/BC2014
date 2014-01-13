package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.robot.TeamRobot;

public abstract class Selector extends Decision {

    public Selector(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean run() throws GameActionException {
        for (int i = 0; i < children.size(); i++) {
            Node current = children.get(i);
            if (current.pre()) {
                if (current.post()) {
                    current.reset();
                }
                boolean run = current.run();
                // if the behavior ran correctly:
                if (run) {
                    if (current.hasPostCalculation()) {
                        current.postCalculations();
                    }
                    return true;
                }
            }
        }

        return false;
    }
}

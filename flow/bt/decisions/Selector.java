package flow.bt.decisions;

import battlecode.common.GameActionException;
import flow.bt.Node;
import flow.robot.TeamRobot;

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
                return current.run();
            }
        }

        return true;
    }
}

package dumbPastHunter.bt.decisions;

import battlecode.common.GameActionException;
import dumbPastHunter.bt.Node;
import dumbPastHunter.robot.TeamRobot;

public abstract class Sequence extends Decision {
    public Sequence(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean run() throws GameActionException {
        Node toRun = children.get(lastRun);
        int counter = 0;
        while ((toRun.post() || !toRun.pre()) && counter < children.size() + 1) {
            counter++;
            lastRun = (lastRun + 1) % children.size();
            if (toRun.post()) {
                toRun.reset();
            }
            toRun = children.get(lastRun);
        }
        if (counter < children.size() + 1) {
            return toRun.run();
        } else {
            rc.setIndicatorString(3, "OMG!! Something is borked.");
            return false;
        }

    }
}

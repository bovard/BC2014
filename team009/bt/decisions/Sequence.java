package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.robot.TeamRobot;

public abstract class Sequence extends Decision {
    public Sequence(TeamRobot robot) {
        super(robot);
    }

    @Override
    public void reset() throws GameActionException {
        lastRun = 0;
    }

    @Override
    public boolean run() throws GameActionException {
        Node toRun = children.get(lastRun);
        int counter = 0;
        boolean done = false;
        while (!done && counter < children.size() + 2) {
            while ((toRun.post() || !toRun.pre()) && counter < children.size() + 1) {
                counter++;
                lastRun = (lastRun + 1) % children.size();
                if (toRun.post()) {
                    toRun.reset();
                }
                toRun = children.get(lastRun);
            }
            if (counter < children.size() + 1) {
                boolean run = toRun.run();
                if (run) {
                    if (toRun.hasPostCalculation()) {
                        toRun.postCalculations();
                    }
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}

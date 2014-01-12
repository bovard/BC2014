package flow.bt.decisions;

import flow.bt.Node;
import flow.robot.TeamRobot;

public abstract class Decision extends Node {
    // store off which of our child nodes we last ran
    protected int lastRun;

    public Decision(TeamRobot robot) {
        super(robot);
    }
}
